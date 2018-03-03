package com.shengsiyuan.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import javax.xml.crypto.Data;

public class ProtoBufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("lipeng")
                .setAge(23)
                .setAddress("苏州御湖湾").build();

        byte[] student2ByteArray = student.toByteArray();

        DataInfo.Student student2 = DataInfo.Student.parseFrom(student2ByteArray);

        System.out.println(student2);
    }


}
