package com.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2020/3/3
 */


//@Component
public class MyConsumer {

    //2个消费者消费同一个队列

    //RabbitListener可以放在类上也可以放在方法上
    @RabbitListener(bindings = @QueueBinding(
            ignoreDeclarationExceptions = "true",
            value = @Queue(
                    name = "springboot_queue",
                    durable = "true"
            ),
            exchange = @Exchange(
                    name = "springboot_exchange",
                    type = ExchangeTypes.DIRECT
            )
            , key = "dxg"

    ))
    @RabbitHandler
    public void get1(org.springframework.messaging.Message<String> message , Channel channel) throws IOException {
        System.err.println("get1");
        MessageHeaders headers = message.getHeaders();
        Long deliveryTag= (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        System.out.println(message.getPayload());
        channel.basicAck(deliveryTag,false);

    }

    @RabbitListener(bindings = @QueueBinding(
            ignoreDeclarationExceptions = "true",
            value = @Queue(
                    name = "springboot_queue",
                    durable = "true"
            ),
            exchange = @Exchange(
                    name = "springboot_exchange",
                    type = ExchangeTypes.DIRECT
            )
            , key = "dxg"

    ))
    @RabbitHandler
    public void get2(org.springframework.amqp.core.Message message,Channel channel) throws IOException {
        System.err.println("get2");
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println(new String(message.getBody(),"utf-8"));
        channel.basicAck(deliveryTag,false);

    }
}
