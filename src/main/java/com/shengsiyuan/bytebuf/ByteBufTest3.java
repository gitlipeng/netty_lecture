package com.shengsiyuan.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufTest3 {
    public static void main(String[] args) {
        ByteBuf heapBuf = Unpooled.buffer(10);
        heapBuf.writeInt(12);

        System.out.println(heapBuf.readableBytes());

        heapBuf.markReaderIndex();

        int length = heapBuf.readInt();

        System.out.println(length);

        System.out.println(heapBuf.readableBytes());

    }
}
