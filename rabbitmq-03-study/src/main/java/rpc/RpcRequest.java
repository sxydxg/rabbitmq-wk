package rpc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import util.RabbitMqConnectionUtils;
import util.SimpleConsumer;

import java.io.IOException;

/**
 * rpc
 *
 * @author dingxigui
 * @date 2020/3/11
 */
public class RpcRequest extends RabbitMqConnectionUtils {

    public static void main(String[] args) throws IOException {

        AMQP.BasicProperties bp = new AMQP.BasicProperties.Builder()
                //设置调用返回消息的队列
                .replyTo("response_rpc")
                //可以设置一个额外的唯一标识
                .correlationId("666")
                .build();

        Channel channel = getChannel();
        //发送rpc请求
        channel.basicPublish("","request_rpc",false,bp,"一个rpc请求".getBytes());


        //接受rpc请求
        DefaultConsumer defaultConsumer = SimpleConsumer.get(channel);
        channel.basicConsume("response_rpc",true,defaultConsumer);


    }



}
