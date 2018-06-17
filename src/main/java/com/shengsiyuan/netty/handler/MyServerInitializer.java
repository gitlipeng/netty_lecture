package com.shengsiyuan.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    //客户端连接后对象被创建并调用
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyByteToLongDecoder2());
        pipeline.addLast(new MyLongToByteEncoder());
        pipeline.addLast("MyServerHandler",new MyServerHandler());//自定义



    }
}






























