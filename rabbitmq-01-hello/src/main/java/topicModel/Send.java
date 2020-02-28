package topicModel;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.DateUtil;
import util.RabbitMqConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *   topic （路由key是通配符匹配）
 *  通配符：  * 是一个单词，#是任意个单词，一般以“.”进行分隔
 * @author dingxigui
 * @date 2019/12/23
 */
public class Send {


    public static void main(String[] args) throws IOException, TimeoutException {

        Connection conn = RabbitMqConnectionUtils.getConn();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare("dxg-topic-exchange", BuiltinExchangeType.TOPIC);

        for (int i=0;i<5;i++){
            String routeKey = "topic.hello" ;
            String msg = DateUtil.toDay()+"---> msg"+i ;
            channel.basicPublish("dxg-topic-exchange",routeKey,false,null,msg.getBytes("utf-8"));

        }
        System.out.println("发送成功！");
        channel.close();

    }
}
