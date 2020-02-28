package ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import util.RabbitMqConnectionUtils;

import java.io.IOException;

/**
 *  测试手动签收
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class Receiver1 {

    public static void main(String[] args) throws IOException {

        final Channel channel = RabbitMqConnectionUtils.getChannel();
        channel.queueDeclare("dxg-queue-ack",false,false,false,null);

        // 创建一个消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body,"utf-8");
                System.out.println("receiver : "+msg);
                //msg03这条消息我们不进行签收
                //消息如果不进行 <签收/不签收>该消息会被独占会导致该消息无法被其他消费者消费，除非该通道挂掉。
                if("msg03".equals(msg)){
                    // 是否重回队列(不重回队列应该会死信队列)
                    // 重回队列该导致该消息被一直被消费
                    getChannel().basicNack(envelope.getDeliveryTag(),false,true);

                }else{
                    //签收（直接使用channel变量也是一样的，会有一个闭包机制，）
                    getChannel().basicAck(envelope.getDeliveryTag(),false);
                }

            }
        };
        //改为手动签收
        channel.basicConsume("dxg-queue-ack",false,defaultConsumer);
    }
}
