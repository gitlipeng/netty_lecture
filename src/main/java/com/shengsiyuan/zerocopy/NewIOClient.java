package com.shengsiyuan.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);

        String fileName = "/Users/lipeng/Desktop/test.mp4";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();

        //这个方法比简单的循环效率高，很多操作系统可以直接从文件系统缓存中传递字节到目标Channel中，没有拷贝过程，拷贝到协议引擎
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println("发送总字节数："+ transferCount + ",耗时: " + (System.currentTimeMillis() - startTime));
    }
}
