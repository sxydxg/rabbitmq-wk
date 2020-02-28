package durable;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import util.RabbitMqConnectionUtils;

import java.io.IOException;

/**
 *
 *  消息的持久化(首先必须要交换机和队列都是持久化的)
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class MessageSend1 {

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqConnectionUtils.getChannel();


        //将队列设置为持久化,不然消息的持久化无效
        channel.queueDeclare("dxg-queue-durable2",true,false,false,null);

        //将消息设置持久化(默认是不持久化的)
        // 构建一个basic属性对象
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.deliveryMode(2);
        AMQP.BasicProperties bp = builder.build();

//        可以使用MessageProperties进行指定
//        AMQP.BasicProperties persistentBasic = MessageProperties.PERSISTENT_BASIC;

        //发送五条消息--------->  格式 : msg+i
        for(int i=0;i<5;i++){
            channel.basicPublish("","dxg-queue-durable2",false,bp,("msg0"+i).getBytes("utf-8"));
        }
        System.out.println("发送成功！");

    }
}
