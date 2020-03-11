package dlx;

import com.rabbitmq.client.*;
import util.RabbitMqConnectionUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class R2 {


    public static void main(String[] args) throws IOException {

        final Channel channel = RabbitMqConnectionUtils.getChannel();


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

        channel.basicConsume("拒绝消息的队列",false,defaultConsumer);

    }
}
