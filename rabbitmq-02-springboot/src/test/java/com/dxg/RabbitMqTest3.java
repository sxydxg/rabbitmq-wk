package com.dxg;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * rabbitmq测试
 *
 *      rabbitmq发送消息时你的交换机或则队列不存在时默认是不创建的，需要由你手动创建
 * @author dingxigui
 * @date 2020/2/29
 */
@SpringBootTest(classes = DemoApplication.class)
public class RabbitMqTest3 extends AbstractTestNGSpringContextTests{

    //rabbitAdmin构造函数内自己new了一个rabbitTemplate
    @Autowired
    private AmqpAdmin amqpAdmin ;

    //写成AmqpTemplate也可以
    @Autowired
    private RabbitTemplate rabbitTemplate;


    //消息转换
    @Test(enabled = false)
    public void test1Convert() throws IOException {

        //这里一直发送消息
        String queue = amqpAdmin.declareQueue(new Queue("convert_queue001"));
        for(int i=0;i<10;i++){
            Message message = MessageBuilder.withBody(("消息" + i).getBytes("utf-8"))
                    .setMessageId("" + i)
                    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                    .build();

            rabbitTemplate.send("",queue,message);
        }


        //消费消息：
        //请查看com.dxg.demo.config.Config2的simpleMessageListenerContainer()方法
        //你可以看到消息为5的一直被消费，形成一个死循环

        //这句代码只是不让程序结束
        System.in.read();
        System.out.println("执行完毕！");
    }
}
