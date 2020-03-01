package com.dxg.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.exception.FatalListenerStartupException;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;


/**
 * 配置类1
 *
 * @author dingxigui
 * @date 2020/2/29
 */
@Configuration
public class Config2 {



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


        //设置确认回调
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause)->{
            //设置手动签收
            System.out.println("correlationData.id : "+correlationData.getId());
            System.out.println("ack : "+ack);
            System.out.println("cause : "+cause);

        });
        return rabbitTemplate ;
    }


    /**
     *   不同业务类型可以有不同消息监听容器
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory){

        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        //设置连接工厂
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        //设置为手动签收
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置要监听的队列
        //发送放要知道交换机或者路由key，但是接受方只要知道queue就行了
        simpleMessageListenerContainer.setQueueNames("ack_queue001");
        //设置消费者数量
        simpleMessageListenerContainer.setConcurrentConsumers(1);
        //设置最大消费者数量
        simpleMessageListenerContainer.setMaxConcurrentConsumers(5);
        //nack 或者失败 是否重回队列（要记得rabbitmq的原生api了嘛）
        simpleMessageListenerContainer.setDefaultRequeueRejected(true);
        //声明重试次数
        simpleMessageListenerContainer.setDeclarationRetries(3);
        //重试间隔时间
        simpleMessageListenerContainer.setRetryDeclarationInterval(1000);
        //设置消息处理监听者
/*        simpleMessageListenerContainer.setMessageListener((Message message)->{
            try {
                System.out.println(new String(message.getBody(),"utf-8"));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });*/
        //这个可以ack
        simpleMessageListenerContainer.setChannelAwareMessageListener((message,channel)->{

            try {
                System.out.println(new String(message.getBody(),"utf-8"));
                //手动签收消息
                long deliveryTag = message.getMessageProperties().getDeliveryTag();
                String messageId = message.getMessageProperties().getMessageId();
                if("5".equals(messageId)){
                    //第三个参数重回队列
                    channel.basicNack(deliveryTag,false,true);
                }else{
                    channel.basicAck(deliveryTag,false);
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        });



        return simpleMessageListenerContainer ;
    }

}
