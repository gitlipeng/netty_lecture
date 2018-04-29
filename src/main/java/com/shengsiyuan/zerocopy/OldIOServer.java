package com.shengsiyuan.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class OldIOServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8899);

        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            try {
                byte[] bytes = new byte[4096];
                int length = 0;

                while (true) {
                    int readCount = dataInputStream.read(bytes, 0, bytes.length);
                    if (readCount == -1) {
                        break;
                    }
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }
    }
}
