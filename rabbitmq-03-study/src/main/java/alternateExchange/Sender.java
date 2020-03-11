package alternateExchange;


import com.rabbitmq.client.*;
import util.RabbitMqConnectionUtils;

import java.io.IOException;
import java.util.HashMap;


/**
 *
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class Sender {

    public static void main(String[] args) throws IOException {

        Channel channel = RabbitMqConnectionUtils.getChannel();

        //声明一个备份交换机，fanout类型
        channel.exchangeDeclare("myAe",BuiltinExchangeType.FANOUT,false,false,null);
        channel.queueDeclare("myAe_queue",false,false,false,null);
        channel.queueBind("myAe_queue","myAe","");


        //使用默认属性即可
        AMQP.BasicProperties bp = new AMQP.BasicProperties.Builder().build();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("alternate-exchange","myAe");
        //声明正常direct类型的交换机,并声明normal_exchange的备份交换机为myAe
        channel.exchangeDeclare("normal_exchange",BuiltinExchangeType.DIRECT,false,false,map);
        channel.queueDeclare("normal_queue",false,false,false,null);
        channel.queueBind("normal_queue","normal_exchange","normal_key");



        //发送消息
        channel.basicPublish("normal_exchange","normal_key",false,null,"一条消息".getBytes());





    }
}
