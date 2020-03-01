package com.dxg;


import com.rabbitmq.client.BuiltinExchangeType;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
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
public class RabbitMqTest2 extends AbstractTestNGSpringContextTests{

    //rabbitAdmin构造函数内自己new了一个rabbitTemplate
    @Autowired
    private AmqpAdmin amqpAdmin ;

    //写成AmqpTemplate也可以
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test(enabled = false)
    public void test1Send() throws UnsupportedEncodingException {
        amqpAdmin.declareQueue(new Queue("hello_queue001"));
        //message构建这非常方便
        Message message = MessageBuilder.withBody("丁溪贵 你好啊！".getBytes("utf-8"))
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setMessageId("123")
                .setHeader("foo", "福")
                .setHeader("foo2","福2")
                .build();
        /**
         *       上面发送的消息格式为：
         *
                 Exchange：	(AMQP default)
                 Routing Key：	hello_queue001
                 Redelivered：	○
                 Properties：
                                 message_id:	123
                                 priority:	0
                                 delivery_mode:	2
                                 headers:
                                            foo:福
                                            foo2:福2
                                content_type:	text/plain

                 Payload        丁溪贵 你好啊！


         *
         *
         */
        //发送往队列中发送消息
        rabbitTemplate.send("","hello_queue001",message);

    }

    //auto ack
    @Test(enabled = true)
    public void test1Recvie() throws UnsupportedEncodingException {
        Message messages = rabbitTemplate.receive("hello_queue001");
        MessageProperties mp = messages.getMessageProperties();
        System.out.println(mp.getDeliveryMode());//rabbitmq原生api别忘了 ，这个是设置消息的持久方式的
        System.out.println(mp.getMessageId());
        System.out.println(mp.getContentType());
        System.out.println(mp.getHeaders());
        System.out.println(new String(messages.getBody(),"utf-8"));
    }

    //手动ack
    @Test(enabled = false)
    public void test2Recvie() throws IOException {

        //这里一直发送消息
        String queue = amqpAdmin.declareQueue(new Queue("ack_queue001"));
        for(int i=0;i<10;i++){
            Message message = MessageBuilder.withBody(("今天是一个阴天" + i).getBytes("utf-8"))
                    .setMessageId("" + i)
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
