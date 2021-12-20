package com.zync.grpc.client2;

import com.zync.grpc.student.lib.ByAgeRequest;
import com.zync.grpc.student.lib.StreamRequest;
import com.zync.grpc.student.lib.StreamResponse;
import com.zync.grpc.student.lib.StudentByAgeResponse;
import com.zync.grpc.student.lib.StudentListResponse;
import com.zync.grpc.student.lib.StudentRequest;
import com.zync.grpc.student.lib.StudentResponse;
import com.zync.grpc.student.lib.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * grpc 客户端2 main 启动
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/20 12:52
 */
@Slf4j
public class GrpcClient2 {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8002).usePlaintext().build();


        // 单向阻塞数据传递
        StudentServiceGrpc.StudentServiceBlockingStub stub = StudentServiceGrpc.newBlockingStub(managedChannel);
        StudentResponse response = stub.getRealName(StudentRequest.newBuilder().setName("张三").build());
        System.out.println(response.getRealName());


        // 单向服务器端流式响应
        Iterator<StudentByAgeResponse> response2 = stub.getRealByAge(ByAgeRequest.newBuilder().setAge("222").build());
        while (response2.hasNext()) {
            StudentByAgeResponse response1 = response2.next();
            System.out.println(response1.getAge());
            System.out.println(response1.getName());
        }


        // 单向客户端端流式请求
        StreamObserver<StudentListResponse> streamObserver = new StreamObserver<StudentListResponse>() {
            @Override
            public void onNext(StudentListResponse value) {
                value.getResponseList().forEach(e -> {
                    System.out.println(e.getName());
                    System.out.println(e.getAge());
                });
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
                System.out.println("Client end.");
            }
        };
        StudentServiceGrpc.StudentServiceStub stub1 = StudentServiceGrpc.newStub(managedChannel);
        StreamObserver<ByAgeRequest> response1 = stub1.getStudentByAge(streamObserver);
        response1.onNext(ByAgeRequest.newBuilder().setAge("22").build());
        System.out.println("------");
        response1.onNext(ByAgeRequest.newBuilder().setAge("23").build());
        response1.onNext(ByAgeRequest.newBuilder().setAge("24").build());
        response1.onNext(ByAgeRequest.newBuilder().setAge("25").build());
        response1.onNext(ByAgeRequest.newBuilder().setAge("26").build());
        response1.onCompleted();
        sleep(5);


        // 双向流式请求和响应
        StudentServiceGrpc.StudentServiceStub stub2 = StudentServiceGrpc.newStub(managedChannel);
        StreamObserver<StreamRequest> requestStreamObserver = stub2.getStudentByBItalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse streamResponse) {
                System.out.println(streamResponse.getResponseInfo());
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
            }
        });

        for (int i = 0; i < 10; i++) {
            requestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(System.currentTimeMillis() + "").build());
            sleep(1);
        }
        sleep(5);
    }

    private static void sleep(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            log.error("线程被中断", e);
            Thread.currentThread().interrupt();
        }
    }
}
