package com.shengsiyuan.nio;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NioServer {

    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException{
        //服务器端只会有一个线程，与传统网络不同，服务器端需要保存客户端的相应信息，才能保证1-1发送
        //要保存的信息：map信息(标识、连接)
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//非阻塞
        ServerSocket serverSocket = serverSocketChannel.socket();

        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//serverSocketChannel注册到selector上

        while (true) {
            try {
                System.out.println("1:等待");
                selector.select();//阻塞，一直等到他关注的事件发生时，返回他关注的事件的数量

                Set<SelectionKey> selectionKeys = selector.selectedKeys();//判断selectionkey是什么样的事件
                System.out.println("2:keyssize:"+selectionKeys.size());
                selectionKeys.forEach(selectionKey -> {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println("3:" + selectionKey.hashCode());
                    final SocketChannel client;
                    try {
                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();//仅仅注册了serverSocketChannel对象，所以这里肯定是ServerSocketChannel
                            client = server.accept();//连接被接受，接下来服务端就通过这个SocketChannel和客户端交互

                            client.configureBlocking(false);//非阻塞
                            client.register(selector, SelectionKey.OP_READ);//关注的事件是OP_READ

                            String key = "[" + UUID.randomUUID().toString() + "]";

                            clientMap.put(key, client);//注册完毕

                        } else if (selectionKey.isReadable()) {
                            //有数据刻度
                            client = (SocketChannel) selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            int count = client.read(readBuffer);
                            if (count > 0) {
                                readBuffer.flip();

                                //写数据
                                Charset charset = Charset.forName("utf-8");
                                String receivedMessage = String.valueOf(charset.decode(readBuffer).array());
//                                System.out.println(receivedMessage);

                                String senderKey = null;

                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    if (client == entry.getValue()) {
                                        senderKey = entry.getKey();
                                        break;
                                    }
                                }

                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                   SocketChannel value = entry.getValue();
                                    if (value != client) {
                                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                        writeBuffer.put((senderKey + ": " + receivedMessage).getBytes());//把数据放到Buffer中 称之为读

                                        writeBuffer.flip();

                                        value.write(writeBuffer);//把数据从Buffer中拿出来称之为写
                                    }

                                }

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
