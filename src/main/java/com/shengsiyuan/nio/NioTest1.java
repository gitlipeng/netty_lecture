package com.shengsiyuan.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class NioTest1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);

        System.out.println(" ");

        for(int i = 0;i < 5; ++i) {
            int randomNumber = new SecureRandom().nextInt(20);//SecureRandom提供了更加健壮的生成器
            buffer.put(i);

            System.out.println("start for  position:" + buffer.position());//  1 2 3 4 5
            System.out.println("start for limit: " + buffer.limit());//10
        }

        System.out.println("before flip  position:" + buffer.position());//5
        System.out.println("before flip limit: " + buffer.limit());//10

        buffer.flip();//反转，读写切换前必须调用

//        buffer.position(1);
//        buffer.put(100);
//        buffer.put(101);
//        buffer.put(102);
//        buffer.put(103);
//        buffer.put(104);
//        buffer.put(105);


        System.out.println("after flip position:" + buffer.position());//0
        System.out.println("after flip limit: " + buffer.limit());//5

        System.out.println("enter while loop");

        System.out.println("position:" + buffer.position());//0 1 2 3 4
        System.out.println("limit: " + buffer.limit()); //5
        System.out.println("capacity: "+buffer.capacity());//10
        System.out.println(buffer.get());

        buffer.flip();
//        buffer.clear();
//
//        buffer.put(100);
//        buffer.put(101);
//        buffer.put(102);
//        buffer.put(103);




        System.out.println("position:" + buffer.position());//0 1 2 3 4
        System.out.println("limit: " + buffer.limit()); //5
        System.out.println("capacity: "+buffer.capacity());//10
        System.out.println(buffer.get());


//
//
//        while (buffer.hasRemaining()) {
//            System.out.println("position:" + buffer.position());//0 1 2 3 4
//
//            System.out.println("limit: " + buffer.limit()); //5
//            System.out.println("capacity: "+buffer.capacity());//10
//            System.out.println(buffer.get());
//        }
    }
}
