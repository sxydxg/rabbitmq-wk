package com.dxg.demo.convert;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.io.UnsupportedEncodingException;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2020/3/1
 */
public class TextMessageConvert implements MessageConverter {

    //java对象变成消息
    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        System.out.println("TextMessageConvert的toMessage执行了");
        System.err.println("toMessage");
        return new Message(object.toString().getBytes(), messageProperties);
    }

    //消息变成java对象
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        System.out.println("TextMessageConvert的fromMessage执行了");

        String contentType = message.getMessageProperties().getContentType();
        try {
            if(null != contentType && contentType.contains("text")) {
                return new String(message.getBody(),"utf-8");
            }
            return message.getBody();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  null ;
    }
}
