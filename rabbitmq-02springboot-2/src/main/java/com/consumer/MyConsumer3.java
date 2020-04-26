package com.consumer;

import com.pojo.Student;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.MessageHeaders;
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
public class MyConsumer3 {



    @RabbitListener(bindings = @QueueBinding(
            ignoreDeclarationExceptions = "true",
            value = @Queue(
                    name = "springboot_queue3",
                    durable = "true"
            ),
            exchange = @Exchange(
                    name = "springboot_exchange3",
                    type = ExchangeTypes.TOPIC
            )
            , key = "dxg"

    ))

    @RabbitHandler
    public void getStudent3(@Payload Student student, Channel channel ,@Headers Map<String,Object> headers) throws IOException {
        System.err.println("getStudent2");

        //通过ListnerContainer容器中的convert类转换 message的body数据，然后注入到@Payload的形参上
        System.out.println(headers.get(AmqpHeaders.CONTENT_TYPE));
        Long deliveryTag= (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        System.out.println(student);
      // channel.basicAck(deliveryTag,false);

    }

}
