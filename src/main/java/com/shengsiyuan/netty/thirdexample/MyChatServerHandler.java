package com.shengsiyuan.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private int count;
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    ExecutorService executorService = Executors.newCachedThreadPool();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();//A发送消息
        final long id = Thread.currentThread().getId();

//            System.out.println("Thread:"+id + "睡眠5秒");
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Thread:"+id + "睡眠5秒结束");

            System.out.println("当前的hashCode：" + this.hashCode() + ",当前的count: " +(++count));

        System.out.println("服务端收到消息：" + msg);

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

        System.out.println("加入Thread:"+Thread.currentThread().getId() + ",hashCode:"+this.hashCode() + ",count:"+ (++count));
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

        System.out.println("上线Thread:"+Thread.currentThread().getId());
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
























