package durable;

import com.rabbitmq.client.Channel;
import util.RabbitMqConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 *  测试交换机的持久化
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class ExchangeSend1 {


    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitMqConnectionUtils.getChannel();

        // 服务器重启交换机消失
//        channel.exchangeDeclare("dxg-exchange-durable","fanout",false,false,null);
       //服务器重启交换机依旧存在
        channel.exchangeDeclare("dxg-exchange-durable","fanout",true,false,null);


    }
}
