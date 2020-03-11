package rpc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import util.RabbitMqConnectionUtils;
import util.SimpleConsumer;

import java.io.IOException;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2020/3/11
 */
public class RpcResponse extends RabbitMqConnectionUtils {

    public static void main(String[] args) throws IOException {



        final Channel channel = getChannel();

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(java.lang.String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //接受请求，然后dowork。。。。
                System.out.println(properties.getCorrelationId());
                System.out.println(new String(body));

                    //将correlationId回送给客户端
                AMQP.BasicProperties bp = new AMQP.BasicProperties.Builder()
                            .correlationId(properties.getCorrelationId())
                            .build();
                String response = "一个rpc响应" ;
                //发送一个rpc响应
                channel.basicPublish("",properties.getReplyTo(),false,bp,response.getBytes());

            }
        };

        channel.basicConsume("request_rpc",true,defaultConsumer);

    }

}
