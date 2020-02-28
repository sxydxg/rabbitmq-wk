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

        //这只是一个普通的队列
        String deadExchange = "test-dlx-exchange";
        String deadQueue = "test-dlx-queue" ;
        String routingKey = "dlx.#" ;

        // 声明了一个死信队列


        HashMap<String, Object> arguments = new HashMap<String, Object>();
        // 固定写法
        arguments.put("x-dead-letter-exchange","dlx.exchange");
        //声明一个队列(将arguments设置进去是最重要的一步)
        channel.queueDeclare(deadQueue,false,false,false,arguments);
        //声明一个交换机
        channel.exchangeDeclare(deadExchange, BuiltinExchangeType.FANOUT);
        //将交换机和队列绑定
        channel.queueBind(deadQueue,deadExchange,routingKey,null);






    }
}
