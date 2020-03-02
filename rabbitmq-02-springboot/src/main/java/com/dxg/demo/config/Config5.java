package com.dxg.demo.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 配置类3
 *
 * @author dingxigui
 * @date 2020/2/29
 */
@Configuration
public class Config5 {



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


    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory){
        //RabbitAdmin是AmqpAdmin的子类
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        //一定设置自动启动不然不会启动
        rabbitAdmin.setAutoStartup(true);
        return  rabbitAdmin ;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate ;
    }





}
