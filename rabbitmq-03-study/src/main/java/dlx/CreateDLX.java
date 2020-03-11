package dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import util.RabbitMqConnectionUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 *    死信队列的声明(其实叫死信交换机和队列的声明)
 *    你可以申明任意类型的交换机(fanout,direct,topic都可以哦!)
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class CreateDLX {

    public static void main(String[] args) throws IOException {

        Channel channel = RabbitMqConnectionUtils.getChannel();

        //声明要给死信交换机
        channel.queueDeclare("死信队列",false,false,false,null);
        channel.exchangeDeclare("死信交换机", BuiltinExchangeType.FANOUT);
        channel.queueBind("死信队列","死信交换机","");

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        // 固定写法
        arguments.put("x-dead-letter-exchange","死信交换机");
        //由于创建的死信交换机为fanout类型，所以不需要设置死信路由键
        // arguments.put("x-dead-letter-routing-key","");

        //声明一个正常的交换机，并将arguments，设置到队列中
        channel.queueDeclare("拒绝消息的队列",false,false,false,arguments);


        channel.queueDeclare("会消息过期的队列",false,false,false,arguments);


    }
}
