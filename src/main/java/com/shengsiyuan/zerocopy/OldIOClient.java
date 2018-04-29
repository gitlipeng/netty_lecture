package com.shengsiyuan.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

public class OldIOClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8899);

        String fileName = "/Users/lipeng/Desktop/test.mp4";

        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        long readcount = 0;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while (true) {
            readcount = inputStream.read(buffer);
            total += readcount;
            if (readcount == -1) {
                break;
            }
            dataOutputStream.write(buffer);
        }

        System.out.println("发送总字节数： " + total + ",耗时: " + (System.currentTimeMillis() - startTime));

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
