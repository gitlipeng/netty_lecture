package com.shengsiyuan.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class NioTest {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress("localhost", 8899));

        int messageLength = 3 + 2 + 4;

        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(3);
        byteBuffers[1] = ByteBuffer.allocate(2);
        byteBuffers[2] = ByteBuffer.allocate(4);


        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            System.out.println("wating...");
            long readLength = 0;
            while (readLength < messageLength) {
                long r = socketChannel.read(byteBuffers);
                readLength += r;

                System.out.println("read:" + r );
                Arrays.stream(byteBuffers).forEach(byteBuffer -> System.out.println("position:" + byteBuffer.position() + ",limit:" + byteBuffer.limit()));

            }

            Arrays.stream(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());

            long writeLength = 0;
            while (writeLength < messageLength) {
                long w = socketChannel.write(byteBuffers);
                writeLength += w;

                System.out.println("write:" + w);
            }

            Arrays.stream(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());

        }

    }
}
