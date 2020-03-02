package com.dxg.demo.pojo;

import java.io.Serializable;

/**
 * 类说明
 *
 * @author dingxigui
 * @date 2020/3/2
 */
public class Student implements Serializable{

    private String name;
    private int age ;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
