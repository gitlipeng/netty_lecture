package com.shengsiyuan.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 10;i++) {
            sendMsg(ctx);
        }

    }

    public void sendMsg(ChannelHandlerContext ctx) {
        int randomInt = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;
        if (0 == randomInt) {
            MyDataInfo.Person person = MyDataInfo.Person.newBuilder().setName("李鹏").setAge(12).setAddress("苏州").build();
            myMessage = MyDataInfo.MyMessage
                    .newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DateType.PersonType)
                    .setPerson(person)
                    .build();

        }else if (1 == randomInt) {
            MyDataInfo.Dog dog = MyDataInfo.Dog.newBuilder()
                    .setName("小黄")
                    .setAge(2)
                    .build();
            myMessage = MyDataInfo.MyMessage
                    .newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DateType.DogType)
                    .setDog(dog)
                    .build();
        }else{
            MyDataInfo.Cat cat = MyDataInfo.Cat.newBuilder()
                    .setName("小新")
                    .setCity("猫的天空之城")
                    .build();
            myMessage = MyDataInfo.MyMessage
                    .newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DateType.CatType)
                    .setCat(cat)
                    .build();
        }

        ctx.channel().writeAndFlush(myMessage);
    }
}
