package dlx;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.RabbitMqConnectionUtils;

import java.io.IOException;

/**
 *  如何进入死信队列
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class S2 {

    public static void main(String[] args) throws IOException {

        Channel channel = RabbitMqConnectionUtils.getChannel();

        // 发送5条消息
        for(int i=0;i<5;i++){
            channel.basicPublish("","拒绝消息的队列",false,null,("msg"+i).getBytes("utf-8"));
        }

    }
}
