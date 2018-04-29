package com.shengsiyuan.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {
    public static void main(String[] args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(8899);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();

        serverSocket.setReuseAddress(true);//Socket连接被关闭后，绑定的端口号超时状态下还不能被其他连接使用，此时，新的socket绑定会提示正在使用，如果在bind之前设置为true，那么这是就能重用这个端口号（处于timewait状态下的）
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            //接收一个连接，
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);

            int readCount = 0;

            while (-1 != readCount) {
                try {
                    readCount = socketChannel.read(byteBuffer);
                } catch (Exception e) {

                }

                byteBuffer.clear();
//                byteBuffer.rewind();
            }


        }


    }
}
