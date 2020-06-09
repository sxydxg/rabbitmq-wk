package a;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQBasicProperties;
import util.RabbitMqConnectionUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @auther 丁溪贵
 * @date 2020/6/9
 */
public class Test1 {


    public static void main(String[] args) throws IOException {

        RabbitMqConnectionUtils.setHost("49.234.123.115");
        Channel channel = RabbitMqConnectionUtils.getChannel();
        //声明一个队列
        channel.queueDeclare("test1",false,false,false,null);

        // 即使设置了messsageId ，消息也是没用的
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
        AMQP.BasicProperties bp = builder.messageId("123").build();

        channel.basicPublish("","test1",bp,"hello2".getBytes("utf-8"));
        channel.basicPublish("","test1",bp,"hello2".getBytes("utf-8"));

    }
}
