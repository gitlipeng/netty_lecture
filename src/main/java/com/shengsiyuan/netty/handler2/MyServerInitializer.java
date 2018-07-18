package com.shengsiyuan.netty.handler2;

import com.shengsiyuan.netty.handler.MyByteToLongDecoder2;
import com.shengsiyuan.netty.handler.MyLongToByteEncoder;
import com.shengsiyuan.netty.handler.MyLongToStringDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    //客户端连接后对象被创建并调用
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println(this);
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("MyServerHandler",new MyServerHandler());//自定义
    }
}






























