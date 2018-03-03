package com.shengsiyuan.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 服务器启动
 * A客户端连接 啥也不做
 * B客户端连接
 * 打印XXX已上线
 * 通知其他客户端XXX已上线
 *
 * A发送消息 ABC都接收消息 A显示自己
 *
 *
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();//A发送消息

        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + "发来了消息：" + msg + "\n");
            }else{
                ch.writeAndFlush("自己发出的消息：" + msg + "\n");

            }
        });

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        //告诉其他客户端XXX上线
        channelGroup.writeAndFlush("[服务器] -"+channel.remoteAddress() + "加入\n");

        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //告诉其他客户端XXX下线
        channelGroup.writeAndFlush("[服务器] -"+channel.remoteAddress() + "离开\n");

//        channelGroup.remove(channel);  netty会自动的寻找，移除，写不写无所谓
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
























