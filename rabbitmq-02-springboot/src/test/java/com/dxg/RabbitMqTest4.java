package com.dxg;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
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
public class RabbitMqTest4 extends AbstractTestNGSpringContextTests{

    //rabbitAdmin构造函数内自己new了一个rabbitTemplate
    @Autowired
    private AmqpAdmin amqpAdmin ;

    //写成AmqpTemplate也可以
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //看看发送端 rabbitTemplate端有什么功能

    //消息的确认机制
    @Test(enabled = false)
    public void test1Confirm() throws IOException {

        //声明一个队列方便测试
        String queue = amqpAdmin.declareQueue(new Queue("test_send_queue",false,false,false,null));
        Message message = MessageBuilder.withBody("今天是一个阴天:2020/3/2".getBytes("utf-8"))
                .setMessageId("666")
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .build();

        //rabbitmq的原始api开启消息的确认机制，不是需要  channel.confirmSelect();
        //那么在spring中去哪里开启这个选项呢，答案在ConnectionFactory当中，请看config4配置类

        //这个就是消息的确认机制
        rabbitTemplate.setConfirmCallback((CorrelationData id, boolean ack, String cause)->{
            System.out.println("CorrelationData "+id);
            if(ack){
                System.out.println(id+"：成功到达broker");
            }else{
                System.out.println(id+"没有到达boker，请进行处理");
            }

        });

        rabbitTemplate.send("",queue,message);

        Message receive = rabbitTemplate.receive(queue);
        System.out.println(new String(receive.getBody(),"utf-8"));

    }

    /**
     *  当发送消息没有正确队列等等问题会将消息返回
     * @throws UnsupportedEncodingException
     */
    @Test(enabled = true)
    public void test2confirm() throws UnsupportedEncodingException {
        Message message = MessageBuilder.withBody("一条无法正确路由的消息".getBytes("utf-8"))
                .setMessageId("111")
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .build();
        //跟原始rabbit的api一样，需要将这个参数开启
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                try {
                    System.err.println(new String(message.getBody(),"utf-8"));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        rabbitTemplate.send("","一个不存在的队列",message);

    }

    public void test2(){

        //有道词典把 ctrl+alt+v 快速返回值得快捷键给冲突了，靠
        //英语翻译：相关 数据
        CorrelationData correlationData = new CorrelationData();
        //该对象就只保存一个与消息相关的id值
        correlationData.setId("123");
        //        rabbitTemplate.setBeforePublishPostProcessors();
        //        rabbitTemplate.setReturnCallback(((Message msg, int replyCode, String replyText,
//                String exchange, String routingKey)->{
//
//        }));

    }
}
