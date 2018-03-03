package com.shengsiyuan.grpc;

import com.shengsiyuan.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GrpcClient {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext(true).build();
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);

        MyResponse myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());
        System.out.println(myResponse.getRealname());

        System.out.println("---------------------------");

        blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(20).build()).forEachRemaining(item->{
            System.out.println("Name:" + item.getName() + ",age:" + item.getAge() + ",city:" + item.getCity());
        });
        System.out.println("---------------------------");

        final CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                value.getStudentResponseList().forEach(item->{
                    System.out.println("Name:" + item.getName() + ",age:" + item.getAge() + ",city:" + item.getCity());
                });
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        };

        StudentServiceGrpc.StudentServiceStub studentServiceStub = StudentServiceGrpc.newStub(managedChannel);//客户端是流式的请求，就是异步的
        StreamObserver<StudentRequest> requestStreamObserver =  studentServiceStub.getStudentsWrapperByAges(studentResponseListStreamObserver);

        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(32).build());
        requestStreamObserver.onCompleted();


        System.out.println("---------------------------");


        StreamObserver<StreamRequest> requestStreamObserver1 = studentServiceStub.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse value) {
                System.out.println(value.getResponseInfo());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        });
        for(int i = 0;i < 10;i++) {
            requestStreamObserver1.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());
        }
        requestStreamObserver1.onCompleted();


        // Receiving happens asynchronously
        finishLatch.await(1, TimeUnit.MINUTES);
    }
}
