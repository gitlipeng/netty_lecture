package com.shengsiyuan.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class ByteBufTest1 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("我hello world", Charset.forName("utf-8"));
        if (byteBuf.hasArray()) {
            //true：堆上缓冲，可以直接调用获取数据

            //33是因为底层字节数组可以扩容，创建新的拷贝到新的，33是预留的，字节编码决定的，3倍*11
            System.out.println(byteBuf);//UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 11, cap: 33)
            System.out.println(byteBuf.arrayOffset());//0,数组偏移量
            System.out.println(byteBuf.readerIndex());//0
            System.out.println(byteBuf.writerIndex());//11
            System.out.println(byteBuf.capacity());//33

            int length = byteBuf.readableBytes();
            System.out.println(length);//11


            System.out.println("******************");
            for(int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println((char)byteBuf.getByte(i));
            }

            System.out.println(byteBuf.getCharSequence(0, 4, Charset.forName("utf-8")));//打印部分
            System.out.println(byteBuf.getCharSequence(4, 6, Charset.forName("utf-8")));//打印部分
        }

    }
}
































