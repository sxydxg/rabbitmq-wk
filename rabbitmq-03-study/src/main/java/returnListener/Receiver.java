package returnListener;

import com.rabbitmq.client.*;
import util.RabbitMqConnectionUtils;

import java.io.IOException;
import java.util.Map;

/**
 *   测试消息头,和消息内详情数据
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class Receiver {

    public static void main(String[] args) throws IOException {

        Channel channel = RabbitMqConnectionUtils.getChannel();
        //声明了一个direct类型的交换机，并进行了绑定
        channel.exchangeDeclare("mandatory_exchange", BuiltinExchangeType.DIRECT,false);
        channel.queueDeclare("madatory_queue",true,false,false,null);
        channel.queueBind("madatory_queue","mandatory_exchange","mandatory_key");
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者收到消息 : "+new String(body));
            }
        };

        //接收消息
        channel.basicConsume("madatory_queue",true,defaultConsumer);

    }
}
