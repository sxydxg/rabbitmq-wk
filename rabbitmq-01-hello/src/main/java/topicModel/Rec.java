package topicModel;

import com.rabbitmq.client.*;
import util.RabbitMqConnectionUtils;

import java.io.IOException;

/**
 * topic （路由key是通配符匹配）
 *     通配符：  * 是一个单词，#是任意个单词，一般以“.”进行分隔
 * @author dingxigui
 * @date 2019/12/23
 */
public class Rec {

    public static void main(String[] args) throws IOException {

        Connection conn = RabbitMqConnectionUtils.getConn();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare("dxg-topic-exchange", BuiltinExchangeType.TOPIC);
        // 队列声明和绑定也可以在 发送端编写（两边都写也可以，因为是幂等性的）
        channel.queueDeclare("dxg-topic-queue",false,false,false,null);
        channel.queueBind("dxg-topic-queue","dxg-topic-exchange","topic.#",null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("接收者： "+new String(body,"utf-8"));
            }
        };

        channel.basicConsume("dxg-topic-queue",true,defaultConsumer);



    }

}
