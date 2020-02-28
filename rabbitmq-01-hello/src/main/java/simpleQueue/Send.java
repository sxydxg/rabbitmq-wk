package simpleQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.DateUtil;
import util.RabbitMqConnectionUtils;

import java.io.IOException;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/18
 */
public class Send {

    public static String queueName = "sxy_simpleQueue" ;
    public static void main(String[] args) throws IOException {

        Connection conn = RabbitMqConnectionUtils.getConn();
        //获取一个通道
        Channel channel = conn.createChannel();
        //声明一个队列（队列的声明是幂等性的，但是状态会被修改）
        channel.queueDeclare(queueName,false,false,false,null);
        //发送的消息
        String msg = DateUtil.toDay()+"-->Hello World! " ;
        channel.basicPublish("",queueName,null,msg.getBytes("utf-8"));

    }


}
