package subscribeModel;

import com.rabbitmq.client.*;
import util.RabbitMqConnectionUtils;

import java.io.IOException;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/18
 */
public class Recv2 {

    private final static String EXCHANGE_NAME = "sxy_exchange_fanout";
    private final static String EXCHANGE_NAME_QUEUE = "sxy_exchange_fanout_queue2";
    public static void main(String[] args) throws IOException {

        Connection conn = RabbitMqConnectionUtils.getConn();
        //创建一个通道
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        channel.queueDeclare(EXCHANGE_NAME_QUEUE,false,false,false,null);
        channel.queueBind(EXCHANGE_NAME_QUEUE,EXCHANGE_NAME,"");
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println(new String(body,"utf-8"));
            }
        };

        channel.basicConsume(EXCHANGE_NAME_QUEUE,true,defaultConsumer);

    }
}
