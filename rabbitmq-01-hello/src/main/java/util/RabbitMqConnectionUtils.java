package util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/18
 */
public class RabbitMqConnectionUtils {
    private static ConnectionFactory connectionFactory = new ConnectionFactory();
    static {
        connectionFactory.setHost("192.168.37.128");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟主机
        connectionFactory.setVirtualHost("/");
    }

    /**
     *  获取一个连接
     * @return
     */
    public static Connection getConn() {

        Connection conn = null ;
        try {
            conn = connectionFactory.newConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn ;
    }
}
