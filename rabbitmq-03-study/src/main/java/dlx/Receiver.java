package dlx;

import com.rabbitmq.client.*;
import util.RabbitMqConnectionUtils;

import java.io.IOException;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class Receiver {


    public static void main(String[] args) throws IOException {

        final Channel channel = RabbitMqConnectionUtils.getChannel();
        channel.queueDeclare("test-queue-1",false,false,false,null);
        channel.exchangeDeclare("test-exchange-1", BuiltinExchangeType.FANOUT);
        channel.queueBind("test-queue-1","test-exchange-1","test-dlx");

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //msg4 不进行签收,且也不重回队列
                if("msg4".equals(new String(body,"utf-8"))){
                    channel.basicNack(envelope.getDeliveryTag(),false,false);
                }else {
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
                System.out.println(new String(body,"utf-8"));
            }
        };

        channel.basicConsume("test-queue-1",false,defaultConsumer);

    }
}
