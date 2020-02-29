package com.dxg.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.callback.CallbackHandler;
import java.nio.channels.Channel;


/**
 * 配置类1
 *
 * @author dingxigui
 * @date 2020/2/29
 */
@Configuration
public class Config1 {



    @Bean
    public ConnectionFactory connectionFactory(){

        //connectionFactory接口为org.springframework.amqp.rabbit.connection.ConnectionFactory;
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost("192.168.37.131");
        cachingConnectionFactory.setPort(5672);
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        //设置虚拟机，一个逻辑概念
        cachingConnectionFactory.setVirtualHost("/");

        return cachingConnectionFactory ;

    }

    /**
     *   这些声明也有用，AmqpAdmin会把这些交换机核队列bean加入自己的容器中，但是此时没有在rabbitmq端对这些目标执行创建
     *   ，只有在执行amqp.Admin的declareXX方法才会真正在Rabbitmq创建这些对象
     *
     */



    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory){
        //RabbitAdmin是AmqpAdmin的子类
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        /**
           public RabbitAdmin(ConnectionFactory connectionFactory) {
             Assert.notNull(connectionFactory, "ConnectionFactory must not be null");
             this.connectionFactory = connectionFactory;
             this.rabbitTemplate = new RabbitTemplate(connectionFactory);
             }
         */
        //一定设置自动启动不然不会启动
        rabbitAdmin.setAutoStartup(true);
        return  rabbitAdmin ;
    }

    @Bean
    public FanoutExchange fanoutExchange001(){
        //按理来说RabbitAdmin或在bean初始后找出所有交换机和队列进行注册，但是这里好像没有
        FanoutExchange fanoutExchange = new FanoutExchange("dxg_default_fanout_exchange", false, false, null);
        return fanoutExchange;
    }

    @Bean
    public Queue queue001(){
        return new Queue("dxg_default_fanout_queue",false,false,false,null);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue001()).to(fanoutExchange001());
    }



    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //rabbitTemplate.setConnectionFactory();

        //设置默认的交换机和队列
        rabbitTemplate.setExchange("dxg_default_fanout_exchange");
        rabbitTemplate.setQueue("dxg_default_fanout_queue");

        return rabbitTemplate ;
    }

}
