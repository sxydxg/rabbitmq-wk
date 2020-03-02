package com.dxg;


import com.dxg.demo.pojo.Student;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
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
public class RabbitMqTest5 extends AbstractTestNGSpringContextTests{

    //rabbitAdmin构造函数内自己new了一个rabbitTemplate
    @Autowired
    private AmqpAdmin amqpAdmin ;

    //写成AmqpTemplate也可以
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test(enabled = false)
    public void test1Confirm() throws IOException {

        //声明一个队列方便测试
        String queue = amqpAdmin.declareQueue(new Queue("queue666"));
        Message message = MessageBuilder.withBody("简单消息".getBytes("utf-8"))
                .setMessageId("111")
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .build();

          //rabbitTemplate.send();这个方法不在讨论，前面这个方法我用的太多了
        //发送消息并接受服务端返回的消息，但是返回的是一个null，不知道声明时候使用这个方法
        Message responseMessage = rabbitTemplate.sendAndReceive("", "queue666", message);
        System.out.println(message==responseMessage); //false
        System.out.println(responseMessage); //null

    }

    @Test(enabled = true)
    public void test2(){

        //声明一个队列方便测试
        String queue = amqpAdmin.declareQueue(new Queue("queue777"));
        Student student = new Student("小明",12);
        //小设置消息转换器
        rabbitTemplate.setMessageConverter(new MessageConverter(){

            @Override
            public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
                System.out.println("MessageConverter.toMessage被调用了");
                //在生产端使用   java对象转换成Message
                //这个就可以用
                if(object instanceof Student){
                    return MessageBuilder.withBody(object.toString().getBytes()).
                            setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).
                            build();
                }
                return null;
            }
            @Override
            public Object fromMessage(Message message) throws MessageConversionException {
                System.out.println("MessageConverter.fromMessage被调用了");
                //这个方法在消费端使用，把消息转换成java对象
                return message;
            }
        });

        //这个方法不知道有什么意义，唉，放他一命吧
//        rabbitTemplate.setMessagePropertiesConverter();
        rabbitTemplate.convertAndSend("","queue777",student);

        //比如在这个时候就会调用fromMessage
//        rabbitTemplate.receiveAndConvert()
    }

}
