package com.shengsiyuan.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

public class NioTest12 {
    public static void main(String[] args) throws IOException {
        //监听5个端口号，每个都可以有客户端连接，不需要多个线程，一个线程处理所有的连接
        int[] ports = new int[5];

        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        Selector selector = Selector.open();//

        System.out.println(SelectorProvider.provider().getClass());

        for(int i = 0;i < ports.length; ++i) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);//非阻塞模式
            ServerSocket serverSocket = serverSocketChannel.socket();//与这个Channel通道关联的ServerSocket
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocket.bind(address);

            //selector注册到channel中，感兴趣的key是接受连接
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("监听端口: " + ports[i]);
        }


        while (true) {
            Set<SelectionKey> selectionKeysss = selector.keys();
            System.out.println("1:" + selectionKeysss.size() + ",2:"+selector.selectedKeys().size());

            int numbers = selector.select();
            System.out.println("numbers: " + numbers);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            System.out.println("selectionKeys + " + selectionKeys + "," + selectionKeys.size());

            Iterator<SelectionKey> iter = selectionKeys.iterator();

            while (iter.hasNext()) {
                SelectionKey selectionKey = iter.next();

                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();//通过key返回绑定的Channle对象
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    iter.remove();

                    System.out.println("获得客户端连接： " + socketChannel);

                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    int bytesRead = 0;

                    while (true) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

                        byteBuffer.clear();

                        int read = socketChannel.read(byteBuffer);

                        if (read <= 0) {
                            break;
                        }

                        byteBuffer.flip();

                        socketChannel.write(byteBuffer);

                        bytesRead += read;
                    }

                    System.out.println("读取： " + bytesRead + ", 来自于: " + socketChannel);

                    iter.remove();//一定要remove 表示用完了
                }
            }
        }
    }
}
