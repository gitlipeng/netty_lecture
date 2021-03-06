package com.shengsiyuan.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    private Server server;

    private void start() throws IOException {
        this.server = ServerBuilder.forPort(8899)
                .addService(new StudentServiceImpl())
                .build()
                .start();//Spring中直接引用StudentServiceImpl

        System.out.println("Server Start");

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("关闭JVM");
            GrpcServer.this.stop();
        }));

        System.out.println("执行到这里");
    }

    private void stop() {
        if (null != this.server) {
            this.server.shutdown();
        }
    }

    private void awaitTermination() throws InterruptedException {
        if (null != this.server) {
            this.server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer();

        server.start();
        server.awaitTermination();

    }
}
