package com.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2020/3/3
 */
@Configuration
public class MyConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin ;
    }


    @Bean
    public RabbitTemplate.ConfirmCallback confirmCallback(){

        return (CorrelationData correlationData, boolean ack, String cause)->{
            if(ack){
                System.out.println("消息成功到达broker");
            }else{
                System.out.println("消息异常，需要处理该消息");
                System.out.println("异常原因："+cause);
            }
        };

    }

    @Bean
    RabbitTemplate.ReturnCallback returnCallback(){

        return (Message message, int replyCode, String replyText,
                String exchange, String routingKey)->{

        };
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,RabbitTemplate.ConfirmCallback confirmCallback,RabbitTemplate.ReturnCallback returnCallback){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
//        默认是简单org.springframework.amqp.support.converter.SimpleMessageConverter，默认时可以替换
//        rabbitTemplate.setMessageConverter();
        return rabbitTemplate ;
    }
}
