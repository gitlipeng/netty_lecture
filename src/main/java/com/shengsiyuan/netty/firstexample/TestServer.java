package com.shengsiyuan.netty.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {
    public static void main(String[] args) throws Exception{
        /**
         * 死循环 线程组
         * NioEventLoopGroup 是用来处理I/O操作的线程池，Netty对 EventLoopGroup 接口针对不同的传输协议提供了不同的实现。
         * 在本例子中，需要实例化两个NioEventLoopGroup，通常第一个称为“boss”，
         * 用来accept客户端连接，另一个称为“worker”，处理客户端数据的读写操作。
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            //启动服务端的类 ServerBootstrap 是启动服务的辅助类，有关socket的参数可以通过ServerBootstrap进行设置。
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//这里指定NioServerSocketChannel类初始化channel用来接受客户端请求。
                    .childHandler(new TestServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

}


























