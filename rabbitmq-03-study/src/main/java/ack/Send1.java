package ack;

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
public class Send1 {


    public static void main(String[] args) throws IOException {

        Channel channel = RabbitMqConnectionUtils.getChannel();
        //发送五条消息--------->  格式 : msg+i
        for(int i=0;i<5;i++){
            channel.basicPublish("","dxg-queue-ack",false,null,("msg0"+i).getBytes("utf-8"));
        }
        System.out.println("发送成功！");
    }
}
