package com.shengsiyuan.grpc;

import com.shengsiyuan.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {
    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        //这里和Thrift不一样
        System.out.println("接收到客户端信息:" + request.getUsername());

        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三").build());
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("接收到的客户端信息：" + request.getAge());

        responseObserver.onNext(StudentResponse.newBuilder().build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("李四").setAge(60).setCity("北京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("王五").setAge(40).setCity("上海").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("赵六").setAge(21).setCity("天津").build());

        responseObserver.onCompleted();;

    }


    /**
     * 返回的是一个请求
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<StudentRequest> getStudentsWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("接收到的客户端信息：" + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
                StudentResponse studentResponse = StudentResponse.newBuilder().setName("张三").setAge(12).setCity("苏州").build();
                StudentResponse studentResponse1 = StudentResponse.newBuilder().setName("李四").setAge(23).setCity("苏州").build();

                responseObserver.onNext(StudentResponseList.newBuilder().addStudentResponse(studentResponse)
                        .addStudentResponse(studentResponse1).build());
                responseObserver.onCompleted();
            }
        };
    }


    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequest>() {
            @Override
            public void onNext(StreamRequest value) {
                System.out.println(value.getRequestInfo());

                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
                responseObserver.onCompleted();
            }
        };
    }
}
