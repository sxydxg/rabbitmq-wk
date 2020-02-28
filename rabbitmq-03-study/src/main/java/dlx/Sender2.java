package dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import util.RabbitMqConnectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class Sender2 {


    public static void main(String[] args) throws IOException {

        Channel channel = RabbitMqConnectionUtils.getChannel();


         channel.queueDeclare("test-queue-2",false,false,false,null);

         //消息过期也过进入死信队列
        AMQP.BasicProperties bp = new AMQP.BasicProperties.Builder()
                .expiration("30000")
                .build();

        channel.basicPublish("","test-queue-2",false,bp,"消息会过期哦".getBytes("utf-8"));


    }
}
