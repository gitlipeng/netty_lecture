package com.shengsiyuan.nio;

import java.nio.ByteBuffer;

/**
 * ByteBuffer 类型化的put与get方法
 */
public class NioTest5 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        byteBuffer.putInt(15);
        byteBuffer.putLong(5000000000L);
        byteBuffer.putDouble(12.454523);
        byteBuffer.putChar('我');
        byteBuffer.putShort((short) 2);
        byteBuffer.putChar('你');

        byteBuffer.flip();

        //适合自定义协议 放进去的是啥 取出来就说啥
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.getChar());
    }
}
