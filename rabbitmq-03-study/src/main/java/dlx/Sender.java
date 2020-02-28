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
public class Sender {

    public static void main(String[] args) throws IOException {

        Channel channel = RabbitMqConnectionUtils.getChannel();
//        队列声明写在消费端即可
//         channel.queueDeclare("test-queue-1",false,false,false,null);
//        channel.exchangeDeclare("test-exchange-1", BuiltinExchangeType.FANOUT);
//        channel.queueBind("test-queue-1","test-exchange-1","test-dlx");

        // 发送5条消息
        for(int i=0;i<5;i++){
            channel.basicPublish("test-exchange-1","test-dlx",false,null,("msg"+i).getBytes("utf-8"));
        }

    }
}
