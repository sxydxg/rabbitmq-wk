package rpc;

import com.rabbitmq.client.Channel;
import util.RabbitMqConnectionUtils;

import java.io.IOException;

/**
 * rpc
 *
 * @author dingxigui
 * @date 2020/3/11
 */
public class Create extends RabbitMqConnectionUtils {

    public static void main(String[] args) throws IOException {
        Channel channel = getChannel();
        channel.queueDeclare("request_rpc",false,false,false,null);
        channel.queueDeclare("response_rpc",false,false,false,null);

    }
}
