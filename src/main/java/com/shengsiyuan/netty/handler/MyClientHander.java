package com.shengsiyuan.netty.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

public class MyClientHander extends SimpleChannelInboundHandler<Long>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("client output:" + ctx.channel().remoteAddress() + ", msg:"+msg);

        ctx.writeAndFlush("from client:"+ LocalDateTime.now());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(123456l);
//        ctx.writeAndFlush(1l);
//        ctx.writeAndFlush(2l);
//        ctx.writeAndFlush(3l);
//        ctx.writeAndFlush(4l);
//        ctx.writeAndFlush("helloworld");

        ctx.writeAndFlush(Unpooled.copiedBuffer("helloworld", Charset.forName("utf-8")));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();//出现异常关闭
    }
}
