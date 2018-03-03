package com.shengsiyuan.netty.secondexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

public class MyClientHander extends SimpleChannelInboundHandler<String>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("client output:" + ctx.channel().remoteAddress() + ", msg:"+msg);

        ctx.writeAndFlush("from client:"+ LocalDateTime.now());
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();//出现异常关闭
    }
}
