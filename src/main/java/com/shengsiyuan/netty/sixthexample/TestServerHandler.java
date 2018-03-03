package com.shengsiyuan.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage>{


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        MyDataInfo.MyMessage.DateType dateType = msg.getDataType();

        switch (dateType) {
            case PersonType:
                MyDataInfo.Person person = msg.getPerson();
                System.out.println(person.getName() + "," + person.getAge() + "," + person.getAddress());
                break;
            case DogType:
                MyDataInfo.Dog dog = msg.getDog();
                System.out.println(dog.getName() + "," + dog.getAge());
                break;
            case CatType:
                MyDataInfo.Cat cat = msg.getCat();
                System.out.println(cat.getName() + "," + cat.getCity());
                break;
        }
    }
}
