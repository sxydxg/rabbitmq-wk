package com.dxg.demo.config;

import com.dxg.demo.convert.MessageDelegate;
import com.dxg.demo.convert.TextMessageConvert;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;


/**
 * 配置类3
 *
 * @author dingxigui
 * @date 2020/2/29
 */
@Configuration
public class Config3 {



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

    /**
     *
     * Confirmation callback.
     * @param correlationData correlation data for the callback.
     * @param ack true for ack, false for nack
     * @param cause An optional cause, for nack, when available, otherwise null.

        void confirm(CorrelationData correlationData, boolean ack, String cause);
     *  @param connectionFactory
     *  @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate ;
    }


    /**
     *   不同业务类型可以有不同消息监听容器
     *   SimpleMessageListenerContainer其所有参数都可以动态修改，包括移除部分监听的队列
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory){

        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        //设置连接工厂
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        //设置为手动签收
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //设置要监听的队列
        //发送放要知道交换机或者路由key，但是接受方只要知道queue就行了
        simpleMessageListenerContainer.setQueueNames("convert_queue001");

        //通过反射调用目标的指定方法
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter();
        //设置message Convert
        //只能设置一个
        messageListenerAdapter.setMessageConverter(new TextMessageConvert());
        messageListenerAdapter.setDelegate(new MessageDelegate());
        messageListenerAdapter.setDefaultListenerMethod("consumeMessage");
        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);

        //simpleMessageListenerContainer.setChannelAwareMessageListener(messageListenerAdapter);
        return simpleMessageListenerContainer ;
    }

}
