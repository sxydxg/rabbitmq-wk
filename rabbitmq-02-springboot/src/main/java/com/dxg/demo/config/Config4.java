package com.dxg.demo.config;

import com.dxg.demo.convert.MessageDelegate;
import com.dxg.demo.convert.TextMessageConvert;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 配置类3
 *
 * @author dingxigui
 * @date 2020/2/29
 */
@Configuration
public class Config4 {



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
        //相当于  channel.confirmSelect();\
        //开启消息的确认模式
        cachingConnectionFactory.setPublisherConfirms(true);
        //开启消息返回机制（个人测试了，这个参数设置与否都不影响ReturnListener的执行）
        cachingConnectionFactory.setPublisherReturns(true);
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
