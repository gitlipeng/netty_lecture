package com.shengsiyuan.nio;

import java.nio.ByteBuffer;

/**
 * 只读Buffer我们可以随时讲一个普通Buffer调用asReadOnlyBuffer方法返回一个只读Buffer
 * 但不能将一个只读Buffer转换为读写Buffer
 */
public class NioTest7 {
    public static void main(String[] args) {
        //只读Buffer
        ByteBuffer buffer = ByteBuffer.allocate(10);// new HeapByteBuffer(capacity, capacity);  在堆上的

        System.out.println(buffer.getClass());

        for(int i = 0; i < buffer.capacity();++i) {
            buffer.put((byte) i);
        }

        ByteBuffer readonlyBuffer = buffer.asReadOnlyBuffer();//方法传递的时候很有用
        System.out.println(readonlyBuffer.getClass());

        readonlyBuffer.position(0);
//        readonlyBuffer.put((byte) 2);

    }
}
