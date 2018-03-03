package com.shengsiyuan.netty.firstexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

//通常会为新SocketChannel通过添加一些handler，来设置ChannelPipeline。
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("httpServiceCodec",new HttpServerCodec());//netty自己的处理器
        pipeline.addLast("testHttpServerHandler",new TestHttpServerHandler());//自定义的处理器
    }
}
