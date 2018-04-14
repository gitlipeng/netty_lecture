package com.shengsiyuan.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest8 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("input2.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("output2.txt");

        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(512);

        while (true) {
            buffer.clear();

            int read = inputChannel.read(buffer);

            System.out.println("read: " + read);
            System.out.println("position: " + buffer.position());
            System.out.println("limit: " + buffer.limit());
            if (-1 == read) {
                break;
            }

            buffer.flip();

            outputChannel.write(buffer);
        }

        inputChannel.close();
        outputChannel.close();
    }
}
