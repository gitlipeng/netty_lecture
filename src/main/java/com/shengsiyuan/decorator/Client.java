package com.shengsiyuan.decorator;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Client {
    public static void main(String[] args) {
        Component component = new ConcreateDecorator1(new ConcreateDecorator2(new ConcreateComponent()));
        component.doSomething();

        InputStream is = new BufferedInputStream(new ByteArrayInputStream("zhangsan".getBytes()));
    }
}
