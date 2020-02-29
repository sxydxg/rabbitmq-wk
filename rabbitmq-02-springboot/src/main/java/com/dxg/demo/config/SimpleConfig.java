package com.dxg.demo.config;




import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/20
 */
//@Configuration
public class SimpleConfig {


    /**
     *  一经启动，这个队列就被声明到rabbitmq之上了（有一个bean处理器处理了。。）
     * @return
     */
    @Bean
    public Queue getQueue(){
        Queue queue = new Queue("dxg",false,false,false,null);
        return queue ;
    }

    @Bean
    public FanoutExchange getFanout(){
        return new FanoutExchange("", false, false, null);
    }

    @Bean
    public Exchange getExchange(){
        //跟使用rabbitmq的channel声明交换差不多
        //声明5中交换机
        FanoutExchange fanoutExchange = new FanoutExchange("", false, false, null);
        DirectExchange directExchange = new DirectExchange("", false, false, null);
        TopicExchange topicExchange = new TopicExchange("", false, false, null);
        //前面3中交换机很熟悉吧！！
        //后面再研究
        //new CustomExchange();
        //new HeadersExchange()
        return null ;
    }

//    @Bean
//    public Binding binding001(){
//        //不同担心不是同一个Exchange对象，他只是获取Exchange的名字而已
//
//    }


/*    @Bean
    public AmqpAdmin getAmqpAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin amqpAdmin = new RabbitAdmin(connectionFactory);
        amqpAdmin.setAutoStartup(true);
        return amqpAdmin ;
    }*/

    @Bean
    public ConnectionFactory getConnectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();

        return cachingConnectionFactory;
    }

    @Bean
    public AmqpTemplate getAmqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //设置默认交换机，路由key
        //你需要半丁交换机和路由key
        rabbitTemplate.setExchange("");
        rabbitTemplate.setRoutingKey("");
        //发送消息，可以指定消息属性进行发送
        Message messsage = new Message("".getBytes(), null);
        //使用你默认设置exchange和routingkey
        rabbitTemplate.send(messsage);
        Message message = rabbitTemplate.sendAndReceive(messsage);
        //帮我们参数转换成message对象
        rabbitTemplate.convertAndSend("");

        //也可以接受消息
        rabbitTemplate.receive();
        return rabbitTemplate ;
    }
}
