package subscribeModel;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.DateUtil;
import util.RabbitMqConnectionUtils;

import java.io.IOException;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：
 * 先运行Send创建交换器
 *
 * 但是这个发送的消息到哪了呢? 消息丢失了!!!因为交换机没有存储消息的能力,在 rabbitmq 中只有队列存储消息的
 * 能力.因为这时还没有队列,所以就会丢失;
 * 小结:消息发送到了一个没有绑定队列的交换机时,消息就会丢失!
 *
 * 【订阅模式】：一个消息被多个消费者消费。
 * 1.一个生产者，多个消费者。
 * 2.每一个消费者都有自己的队列。(队列如果相同的话，一条消息只会被一个消费者消费)
 * 3.生产者没有直接把消息发送到队列，而是发送到了交换机、转发器exchange
 * 4.每个队列都要绑定到交换机上
 * 5.生产者发送的消息经过交换机到达队列，就能实现一个消息被多个消费者消费。
 *
 * 邮件->注册->短信
 *
 * </p>
 */
public class Send {

    private final static String EXCHANGE_NAME = "sxy_exchange_fanout";
    public static void main(String[] args) throws IOException {

        Connection conn = RabbitMqConnectionUtils.getConn();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String msg = DateUtil.toDay()+"--->Hello World!" ;

        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes("utf-8"));


    }
}
