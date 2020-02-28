package durable;

import com.rabbitmq.client.Channel;
import util.RabbitMqConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 *  测试队列的持久化
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class QueueSend1 {


    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitMqConnectionUtils.getChannel();

        //将队列设置为非持久化(服务器重启该队列中会消失)
        channel.queueDeclare("dxg-queue-durable",false,false,false,null);
        //将队列设置为持久化(服务器重启该队列依然存在)
//        channel.queueDeclare("dxg-queue-durable",true,false,false,null);


        //发送五条消息--------->  格式 : msg+i
        for(int i=0;i<5;i++){
            channel.basicPublish("","dxg-queue-durable",false,null,("msg0"+i).getBytes("utf-8"));
        }
        System.out.println("发送成功！");

    }
}
