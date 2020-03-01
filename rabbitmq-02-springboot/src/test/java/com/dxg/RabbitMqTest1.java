package com.dxg;


import com.rabbitmq.client.BuiltinExchangeType;
import net.bytebuddy.asm.Advice;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;


/**
 * rabbitmq测试
 *
 *      rabbitmq发送消息时你的交换机或则队列不存在时默认是不创建的，需要由你手动创建
 *
 *      该test例子使用Config1作为配置    com.dxg.demo.config.Config1 （没有打开哦！！！）
 *
 * @author dingxigui
 * @date 2020/2/29
 */
@SpringBootTest(classes = DemoApplication.class)
public class RabbitMqTest1 extends AbstractTestNGSpringContextTests{

    //rabbitAdmin构造函数内自己new了一个rabbitTemplate
    @Autowired
    private AmqpAdmin amqpAdmin ;

    //写成AmqpTemplate也可以
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //创建并绑定交换机
    @Test(enabled = false)
    public void test1AmqpAdmin(){

        //例子1：创建交换机并且绑定
        //Exchange有多个子类，spring帮我们抽象了，不然就要写字符串了
        //fanout类型   直连交换机
        FanoutExchange dxg_fanout = new FanoutExchange("dxg_fanout", false, false, null);
        Queue dxg_fanout_queue = new Queue("dxg_fanout_queue", false, false, false, null);
        //将队列dxg_queue绑定到交换机dxg_fanout上
        Binding fanout_binding = BindingBuilder.bind(dxg_fanout_queue).to(dxg_fanout);
        amqpAdmin.declareExchange(dxg_fanout);
        amqpAdmin.declareQueue(dxg_fanout_queue);
        amqpAdmin.declareBinding(fanout_binding);

        //topic or direct（这2个差不多,在这里只演示一个）
        TopicExchange dxg_topic = new TopicExchange("dxg_topic", false, false,null);
        Queue dxg_topic_queue = new Queue("dxg_topic_queue", false, false, false, null);
        Binding topic_binding = BindingBuilder.bind(dxg_topic_queue).to(dxg_topic).with("dxg.#");
        amqpAdmin.declareExchange(dxg_topic);
        amqpAdmin.declareQueue(dxg_topic_queue);
        amqpAdmin.declareBinding(topic_binding);

    }

    @Test(enabled = false)
    public void test2(){
        amqpAdmin.declareQueue(new Queue("purgeQueue001"));
        //净化一个队列(将队列中的消息清空)
        amqpAdmin.purgeQueue("purgeQueue001",true);
        //删除个交换机
        amqpAdmin.deleteExchange("dxg_fanout");
        amqpAdmin.deleteExchange("dxg_topic");
        //删除个队列
        amqpAdmin.deleteQueue("dxg_fanout_queue");
        amqpAdmin.deleteQueue("dxg_topic_queue");

    }

    //发送消息需要rabbitTemplate
    @Test(enabled = false)
    public void  test3SendMessage() throws UnsupportedEncodingException {

        //1.往rabbitTemplate设置的交换机和队列里面发送消息
        MessageProperties messageProperties = new MessageProperties();
        Message message = new Message("hello".getBytes("utf-8"),messageProperties );
        rabbitTemplate.send(message);

        MessageProperties messageProperties2 = new MessageProperties();
        Message message2 = new Message("hello2".getBytes("utf-8"),messageProperties2 );
        //一般使用RabbitAdmin来创建交换机或者队列，这里只是玩玩别的方式
        //声明一个topic （这里使用rabbitmq的原生api声明交换机和queue）
        rabbitTemplate.execute(channel -> {
            channel.exchangeDeclare("exchange001", BuiltinExchangeType.TOPIC,false,false,null);
            channel.queueDeclare("queue001",false,false,false,null);
            channel.queueBind("queue001","exchange001","dxg");
            return null ;
        });

       rabbitTemplate.send("exchange001","dxg",message2);

    }

    //往单个队列中发送消息
    @Test(enabled = false)
    public void test4SendMessage() throws UnsupportedEncodingException {

        MessageProperties messageProperties = new MessageProperties();
        Message message = new Message("hello".getBytes("utf-8"),messageProperties );
        amqpAdmin.declareQueue(new Queue("queue002"));
        //往单个队列中发送消息
        //amqp中往单给队列中发送消息，那么队列名当中routingKey
        //这个跟rabbitmq的原生api发送方式一模一样
        rabbitTemplate.send("","queue002",message);

    }

    //往fanout交换机中发送消息
    @Test(enabled = false)
    public void test5SendMessage() throws UnsupportedEncodingException {

        FanoutExchange dxg_fanout = new FanoutExchange("fanout0003", false, false, null);
        Queue dxg_fanout_queue = new Queue("queue0003", false, false, false, null);
        //将队列dxg_queue绑定到交换机dxg_fanout上
        Binding fanout_binding = BindingBuilder.bind(dxg_fanout_queue).to(dxg_fanout);
        amqpAdmin.declareExchange(dxg_fanout);
        amqpAdmin.declareQueue(dxg_fanout_queue);
        amqpAdmin.declareBinding(fanout_binding);

        MessageProperties messageProperties = new MessageProperties();
        Message message = new Message("hello".getBytes("utf-8"),messageProperties );
        rabbitTemplate.send("fanout0003","",message);

    }

}
