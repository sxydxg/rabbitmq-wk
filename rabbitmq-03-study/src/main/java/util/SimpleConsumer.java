package util;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2020/3/11
 */
public class SimpleConsumer extends RabbitMqConnectionUtils{

   public static void start(String queue) throws IOException {
       Channel channel = getChannel();
       channel.basicConsume(queue,true,get(channel));
   }

   public static DefaultConsumer get(Channel channel){
       return new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               if(properties.getCorrelationId()!=null){
                   System.out.println(properties.getCorrelationId());
               }
               System.out.println(new String(body));
           }
       };

   }
}
