package header;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import util.RabbitMqConnectionUtils;

import java.io.IOException;
import java.util.Map;

/**
 *   测试消息头,和消息内详情数据
 *
 * @author dingxigui
 * @date 2019/12/23
 */
public class Receiver {

    public static void main(String[] args) throws IOException {

        final Channel channel = RabbitMqConnectionUtils.getChannel();
        channel.queueDeclare("dxg-queue-header",false,false,false,null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("consumerTag--->"+consumerTag);
                // envelope :
                System.out.println("envelope: ");
                System.out.println("    exchange : "+envelope.getExchange());
                System.out.println("    routingKey : "+envelope.getRoutingKey());
                System.out.println("    deliveryTag : "+envelope.getDeliveryTag());
                // header(就是一个map,由用户自定义)
                Map<String, Object> headers = properties.getHeaders();
                System.out.println("headers : ");
                System.out.println("    名字 : "+headers.get("name"));
                System.out.println("    技能 : "+headers.get("jn"));
                System.out.println();
                System.out.println("properties : "+properties.toString());

            }
        };

        channel.basicConsume("dxg-queue-header",true,defaultConsumer);
    }
}
