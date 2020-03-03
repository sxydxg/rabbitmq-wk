package com.consumer;

import com.pojo.Student;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2020/3/3
 */


@Component
public class MyConsumer2 {

    //2个消费者消费同一个队列

    //RabbitListener可以放在类上也可以放在方法上
    @RabbitListener(bindings = @QueueBinding(
            ignoreDeclarationExceptions = "true",
            value = @Queue(
                    name = "springboot_queue2",
                    durable = "true"
            ),
            exchange = @Exchange(
                    name = "springboot_exchange2",
                    type = ExchangeTypes.DIRECT
            )
            , key = "dxg"

    ))
    @RabbitHandler
    public void getStudent(org.springframework.messaging.Message<Student> message , Channel channel) throws IOException {
        System.err.println("getStudent");

        MessageHeaders headers = message.getHeaders();
        System.out.println(headers.get(AmqpHeaders.CONTENT_TYPE));
        Long deliveryTag= (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        Student student = message.getPayload();
        System.out.println(student);
        channel.basicAck(deliveryTag,false);

    }


    @RabbitListener(bindings = @QueueBinding(
            ignoreDeclarationExceptions = "true",
            value = @Queue(
                    name = "springboot_queue2",
                    durable = "true"
            ),
            exchange = @Exchange(
                    name = "springboot_exchange2",
                    type = ExchangeTypes.DIRECT
            )
            , key = "dxg"

    ))

    @RabbitHandler
    public void getStudent2(@Payload Student student, Channel channel ,@Headers Map<String,Object> headers) throws IOException {
        System.err.println("getStudent2");

        //通过ListnerContainer容器中的convert类转换 message的body数据，然后注入到@Payload的形参上
        System.out.println(headers.get(AmqpHeaders.CONTENT_TYPE));
        Long deliveryTag= (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        System.out.println(student);
        channel.basicAck(deliveryTag,false);

    }

}
