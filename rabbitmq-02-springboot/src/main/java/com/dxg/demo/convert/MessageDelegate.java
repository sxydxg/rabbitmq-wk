package com.dxg.demo.convert;

import java.io.UnsupportedEncodingException;

/**
 *
 *
 * @author dingxigui
 * @date 2020/3/1
 */
public class MessageDelegate {

    public void handleMessage(byte[] messageBody) throws UnsupportedEncodingException {
        System.out.println(new String(messageBody,"utf-8"));
    }

    public void consumeMessage(String messageBody){
        System.out.println("MessageDelegate : "+messageBody);
    }
}
