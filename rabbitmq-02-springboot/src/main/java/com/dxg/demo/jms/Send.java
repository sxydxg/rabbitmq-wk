package com.dxg.demo.jms;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/20
 */
@Component
public class Send {

    @Autowired
    private AmqpTemplate amqpTemplate ;


    public void send(String msg){
//        Message message = new Message(msg.getBytes(), null);
//        amqpTemplate.send(message);
        amqpTemplate.convertAndSend("springboot-helloworld-queue",msg);

    }

}
