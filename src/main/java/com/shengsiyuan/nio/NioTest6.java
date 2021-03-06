package com.shengsiyuan.nio;

import java.nio.ByteBuffer;

public class NioTest6 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for(int i = 0; i < buffer.capacity();++i) {
            buffer.put((byte) i);
//            System.out.println("position:" + buffer.position());
        }

        buffer.position(2);
        buffer.limit(6);

        ByteBuffer sliceBuffer = buffer.slice();//两个buffer的limit position都是独立的了,但是与原有的Buffer共享相同的底层数组


        System.out.println("sliceBuffer:position:" + sliceBuffer.position() + ",limit:" + sliceBuffer.limit());
        for(int i = 0;i < sliceBuffer.capacity();++i) {
            byte b = sliceBuffer.get(i);
            b *= 2;
            sliceBuffer.put(i,b);//这里不会修改position和limit
            System.out.println("b:" + b );
        }
        System.out.println(sliceBuffer.position() + "," + sliceBuffer.limit());

        System.out.println(sliceBuffer.position() + "," + sliceBuffer.limit());

        while (sliceBuffer.hasRemaining()) {
            System.out.println(sliceBuffer.get());
        }

    }
}
