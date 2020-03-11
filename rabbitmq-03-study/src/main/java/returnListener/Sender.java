package returnListener;


import com.rabbitmq.client.*;
import util.RabbitMqConnectionUtils;

import java.io.IOException;


/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class Sender {

    public static void main(String[] args) throws IOException {

        Channel channel = RabbitMqConnectionUtils.getChannel();

        //使用默认属性即可
        AMQP.BasicProperties bp = new AMQP.BasicProperties.Builder().build();


        channel.addReturnListener(new ReturnCallback() {
            public void handle(Return returnMessage) {

                System.out.println(returnMessage.getExchange());
                System.out.println(returnMessage.getRoutingKey());
                System.out.println(returnMessage.getReplyCode()+"/"+returnMessage.getReplyText());
                System.out.println(new String(returnMessage.getBody()));
            }
        });

       /* channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });*/

        channel.basicPublish("mandatory_exchange","mandatory_key",true,null,"一条消息".getBytes());





    }
}
