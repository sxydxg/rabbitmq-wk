package workQueue;

import com.rabbitmq.client.*;
import util.RabbitMqConnectionUtils;

import java.io.IOException;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/18
 */
public class Recv1 {
    public static String queueName = "sxy_workQueue" ;
    public static void main(String[] args) throws IOException {

        Connection conn = RabbitMqConnectionUtils.getConn();
        //创建一个通道
        Channel channel = conn.createChannel();
//        channel.basicQos(2); 没什么作用
        //声明一个队列（队列的声明是幂等性的，但是状态会被修改）
        AMQP.Queue.DeclareOk queue = channel.queueDeclare(queueName, false, false, false, null);
        //创建一个消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {

            // 处理接收
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body,"utf-8"));
            }
        };

        channel.basicConsume(queueName,true,defaultConsumer);


    }



}
