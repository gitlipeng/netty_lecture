package com.shengsiyuan.netty.fifthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class WebSocketHandler extends SimpleChannelInboundHandler<HttpObjectAggregator> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObjectAggregator msg) throws Exception {
        System.out.println(msg);
    }
}
