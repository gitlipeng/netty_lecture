package com.shengsiyuan.netty.handler3;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("MyPersonDecoder",new MyPersonDecoder());
        pipeline.addLast("MyPersonEncoder",new MyPersonEncoder());

        pipeline.addLast(new MyClientHandler());


    }
}
