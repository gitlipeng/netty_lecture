package com.shengsiyuan.nio;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步编程模型 单线程 提升效率
 */
public class NioClient {
    public static void main(String[] args) throws IOException {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);//客户端向服务端发起连接
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));

            while (true) {
                selector.select();//阻塞，表示服务端没有返回东西，等待返回
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                for (SelectionKey selectionKey : selectionKeys) {
                    if (selectionKey.isConnectable()) { //表示与服务端已经建立好了连接
                        SocketChannel client = (SocketChannel)selectionKey.channel();

                        if (client.isConnectionPending()) {
                            client.finishConnect();//完成连接

                            //连接建立好了
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((LocalDateTime.now() + " 链接成功").getBytes());
                            writeBuffer.flip();
                            client.write(writeBuffer);

                            //连接好了 启动个线程 等待用户输入
                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() -> {
                                while ((true)) {
                                    System.out.println("wait");
                                    writeBuffer.clear();

                                    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                    BufferedReader br = new BufferedReader(inputStreamReader);
                                    String sendMessage = br.readLine();

                                    writeBuffer.put(sendMessage.getBytes());
                                    writeBuffer.flip();
                                    client.write(writeBuffer);

                                }
                            });
                        }

                        client.register(selector, SelectionKey.OP_READ);
                    }
                    else if (selectionKey.isReadable()) {
                        SocketChannel client = (SocketChannel)selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int count = client.read(readBuffer);
                        if (count > 0) {
                            String receivedMessage = new String(readBuffer.array(), 0, count);
                            System.out.println(receivedMessage);
                        }
                    }
                }
                selectionKeys.clear();
            }
        } catch (Exception e) {

        }
    }
}
