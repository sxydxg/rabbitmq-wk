package com;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2020/3/3
 */
@SpringBootApplication
@EnableRabbit //注册rabbitList和rabbitHandler处理bean
public class SpringBoot2Demo {

    public static void main(String[] args) {
        SpringApplication.run(SpringApplication.class,args);
    }
}
