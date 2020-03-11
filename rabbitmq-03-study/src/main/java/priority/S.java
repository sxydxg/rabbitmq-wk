package priority;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import util.RabbitMqConnectionUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 *
 *
 * @author dingxigui
 * @date 2020/3/11
 */
public class S extends RabbitMqConnectionUtils {

    public static void main(String[] args) throws IOException {
        //关于有优先机队列条件：  生产者发送消息的速度应该远大于消费者不然就没有意义了
        Channel channel = getChannel();
        HashMap<String, Object> map = new HashMap<String, Object>();
        //设置队列的最大优先级 为10
        map.put("x-max-priority",10);
        channel.queueDeclare("优先级队列",false,false,false,map);

        AMQP.BasicProperties bp = null ;
        //发送一万条消息
        for (int i=0;i<1000;i++){
            //只要单数我优先级设置为最高

            if(i%2==1){
                bp = new AMQP.BasicProperties.Builder().priority(10).build();
            }
            channel.basicPublish("","优先级队列",false,bp,("msg"+i).getBytes());
            bp=null ;
        }




    }


}
