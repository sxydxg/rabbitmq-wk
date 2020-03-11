package returnListener.warn1;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.ReturnListener;
import util.RabbitMqConnectionUtils;

import java.io.IOException;


/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class Sender1 {

    public static void main(String[] args) throws IOException {

        Channel channel = RabbitMqConnectionUtils.getChannel();

        //使用默认属性即可
        AMQP.BasicProperties bp = new AMQP.BasicProperties.Builder().build();

        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });

        channel.basicPublish("mandatory_exchange_1","",true,null,"一条消息".getBytes());





    }
}
