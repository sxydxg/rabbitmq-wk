package com.test;

import com.SpringBoot2Demo;
import com.pojo.Student;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 *
 *
 * @author dingxigui
 * @date 2020/3/3
 */

@SpringBootTest(classes = SpringBoot2Demo.class)
public class RabbitTest1 extends AbstractTestNGSpringContextTests{

    @Autowired
    private RabbitTemplate rabbitTemplate ;

    @Autowired
    private RabbitAdmin rabbitAdmin ;

    @Test(enabled = false)
    public void test1(){
        //看了一下打印默认是这个类：class org.springframework.amqp.support.converter.SimpleMessageConverter
        System.out.println(rabbitTemplate.getMessageConverter().getClass());
        //class org.springframework.amqp.rabbit.connection.CachingConnectionFactory
        System.out.println(rabbitTemplate.getConnectionFactory().getClass());

    }


    @Test(enabled = false)
    public void send(){
        rabbitAdmin.declareQueue(new Queue("springboot_queue"));
        rabbitAdmin.declareExchange(new DirectExchange("springboot_exchange"));
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("springboot_queue")).to(new DirectExchange("springboot_exchange")).with("dxg"));
        //往里面发送消息
        for (int i=0;i<10;i++){
            rabbitTemplate.convertAndSend("springboot_exchange","dxg","springboot_message"+i);
        }


    }

    @Test
    public void send2(){
        rabbitAdmin.declareQueue(new Queue("springboot_queue2"));
        rabbitAdmin.declareExchange(new DirectExchange("springboot_exchange2"));
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("springboot_queue2")).to(new DirectExchange("springboot_exchange2")).with("dxg"));
        //往里面发送消息
        for (int i=0;i<10;i++){
            Student student = new Student("d" + i, i * 10);
            rabbitTemplate.convertAndSend("springboot_exchange2","dxg",student);
        }


    }

}
