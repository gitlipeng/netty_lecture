package com.shengsiyuan.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

public class NioServer2 {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys =  selector.selectedKeys();

                selectionKeys.forEach(key -> {
                    try {
                        final SocketChannel client;
                        if (key.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel) key.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            client = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                            int count = client.read(byteBuffer);
                            if (count > 0) {
                                byteBuffer.flip();

                                Charset charset = Charset.forName("utf-8");
                                String receiverMsg = String.valueOf(charset.decode(byteBuffer).array());
                                System.out.println(receiverMsg);
                            }
                        }
//                        selectionKeys.remove(key);
                    } catch (Exception e) {

                    }
                });



            } catch (Exception e) {

            }
        }

    }
}
