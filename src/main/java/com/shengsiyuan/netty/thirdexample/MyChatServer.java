package com.shengsiyuan.netty.thirdexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyChatServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup boodGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boodGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyChatServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            boodGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}








































