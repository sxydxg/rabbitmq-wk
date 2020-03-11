package ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import util.RabbitMqConnectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ttl和死信队列
 *
 * @author dingxigui
 * @date 2020/3/11
 */
public class S extends RabbitMqConnectionUtils{


    public static void main(String[] args) throws IOException {

        Channel channel = getChannel();
        HashMap<String, Object> map = new HashMap<String, Object>();
        //massage==按摩，message==消息，参数别看错了
        map.put("x-message-ttl",10000);
        //为整个队列设置消息的过期时间
        channel.queueDeclare("ttl_queue",false,false,false, map);

        AMQP.BasicProperties bp = null ;

        for (int i=0;i<100;i++){
            //当队列和消息都声明了消息的过期时间，那么以最小的为准
            //消息为最前面的才有效
            //设计的时候每条消息的过期时间应该保持相同
            if(i==0||i==1||i==20){
                bp = new AMQP.BasicProperties.Builder().expiration("3000").deliveryMode(2).build();
            }
            //i=20的消息并不会在3秒后过期
            channel.basicPublish("","ttl_queue",bp,"一条会过期的消息".getBytes());
            bp=null;
        }



    }


}
