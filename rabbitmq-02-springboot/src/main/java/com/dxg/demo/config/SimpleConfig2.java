package com.dxg.demo.config;




import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/20
 */
@Configuration
public class SimpleConfig2 {





    @Bean
    public AmqpAdmin getAmqpAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin amqpAdmin = new RabbitAdmin(connectionFactory);
        amqpAdmin.setAutoStartup(true);

        return amqpAdmin ;
    }
    @Bean
    public RabbitAdmin getRabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin amqpAdmin = new RabbitAdmin(connectionFactory);
        amqpAdmin.setAutoStartup(true);
        
        return amqpAdmin ;
    }

}
