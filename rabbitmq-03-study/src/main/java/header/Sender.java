package header;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import util.RabbitMqConnectionUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class Sender {

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqConnectionUtils.getChannel();
        channel.queueDeclare("dxg-queue-header",false,false,false,null);

        //可以看一下这个类的常量,因为持久化为 2 就是从这里看出来的.
        //AMQP.BasicProperties persistentBasic = MessageProperties.PERSISTENT_BASIC;
        HashMap<String, Object> map = new HashMap<String, Object> ();
        map.put("name","十方");
        map.put("jn","大日如来经");

        // 构建一个消息属性
        AMQP.BasicProperties bp = new AMQP.BasicProperties.Builder().
                deliveryMode(2)
                .expiration("20000")
                .headers(map)
                .build();


        channel.basicPublish("","dxg-queue-header",false,bp,"".getBytes());

    }
}
