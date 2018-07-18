package com.shengsiyuan.netty.handler3;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    //客户端连接后对象被创建并调用
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println(this);
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("MyPersonDecoder",new MyPersonDecoder());
        pipeline.addLast("MyPersonEncoder",new MyPersonEncoder());

        pipeline.addLast("MyServerHandler",new MyServerHandler());
    }
}






























