package com.dxg.demo.jms;

import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2019/12/20
 */
@Component
public class Recv {

    /**
    * 监听消息
    * @author dingxigui
    * @date 2019/12/20
    * @param
    * @return
    */
    @RabbitListener(queues="springboot-helloworld-queue")
    public void receive(String msg){
        System.out.println("receive : "+msg);
    }

}
