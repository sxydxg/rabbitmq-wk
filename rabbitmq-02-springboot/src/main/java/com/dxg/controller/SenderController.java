package com.dxg.controller;

import com.dxg.demo.jms.Send;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/20
 */
@RestController
public class SenderController {

    @Autowired
    private AmqpAdmin amqpAdmin ;

    @Autowired
    private RabbitTemplate  rabbitTemplate ;

    @PostMapping("/rb/send")
    public String send(String msg){
        amqpAdmin.declareQueue(new Queue("aaaa"));

        System.out.println("aaa");
        return msg ;
    }

    @PostMapping("/rb/send2")
    public String send2(String msg){
        System.out.println(rabbitTemplate);
        rabbitTemplate.setExchange("");
        rabbitTemplate.setRoutingKey("hello2");
        rabbitTemplate.setQueue("hello");
        rabbitTemplate.send(new Message("hhhh".getBytes(),null));

        return msg ;
    }
}
