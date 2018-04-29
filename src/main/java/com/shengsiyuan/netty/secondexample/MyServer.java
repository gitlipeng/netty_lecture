package com.shengsiyuan.netty.secondexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * EventLoopGroup：特殊的EventExecutorGroup，用来在后续的事件循环过程中，在进行Selector的select操作时注册一个个相应的与客户端的连接Channel
 *
 * EventLoop next(); 返回下一个事件循环
 * ChannelFuture register(Channel channel);:将一个连接注册到循环中，注册完之后会异步通知Future
 * ChannelFuture register(ChannelPromise promise);：
 *
 * newChild()
 *
 * EventExecutor:

 */
public class MyServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();//异步的事件循环组（死循环），参数表示线程数，通常设置成1，bossGroup不处理其他东西，一个线程足够 ,NIO  Selector based Channels.
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();//辅助类 没做其他多余的事，设置属性 参数

            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
            .childHandler(new MyServerInitializer());//handler处理的boos childHandler处理worker

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }



    }
}
