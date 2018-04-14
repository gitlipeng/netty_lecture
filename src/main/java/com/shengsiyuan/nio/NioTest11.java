package com.shengsiyuan.nio;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 关于Buffer的Scattering（分散）与Gathering（收集）
 * 网络操作的自定义协议 消息体长度不定
 * 执行 nc localhost 8899
 */
public class NioTest11 {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);

        int messageLength = 2 + 3 + 4;

        ByteBuffer[] buffers = new ByteBuffer[3];

        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            int bytesRead = 0;
            while (bytesRead < messageLength) {
                long r = socketChannel.read(buffers);
                bytesRead += r;

                System.out.println("bytesRead: " + bytesRead);

                Arrays.asList(buffers).stream().map(buffer -> "position: " + buffer.position() + ", limit: " + buffer.limit()).forEach(System.out::println);

            }
            Arrays.asList(buffers).forEach(byteBuffer -> byteBuffer.flip());

            long bytesWriteen = 0;
            while (bytesWriteen < messageLength) {
                long r = socketChannel.write(buffers);
                bytesWriteen += r;
            }

            Arrays.asList(buffers).forEach(byteBuffer -> byteBuffer.clear());

            System.out.println("bytesRead: " + bytesRead + ", bytesWritten: " + bytesWriteen + ",");
        }
    }
}
