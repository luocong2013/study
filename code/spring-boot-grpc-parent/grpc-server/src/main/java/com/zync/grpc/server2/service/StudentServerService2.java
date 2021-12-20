package com.zync.grpc.server2.service;

import com.zync.grpc.student.lib.StudentByAgeResponse;
import com.zync.grpc.student.lib.StudentRequest;
import com.zync.grpc.student.lib.StudentResponse;
import com.zync.grpc.student.lib.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Student grpc 服务2
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/20 11:47
 */
@Slf4j
public class StudentServerService2 extends StudentServiceGrpc.StudentServiceImplBase {

    /**
     * 单向阻塞数据传递
     * @param request
     * @param responseObserver
     */
    @Override
    public void getRealName(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        responseObserver.onNext(StudentResponse.newBuilder().setRealName(request.getName() + "_Real").build());
        responseObserver.onCompleted();
    }

    /**
     * 单向服务器端流式响应
     * @param request
     * @param responseObserver
     */
    @Override
    public void getRealByAge(com.zync.grpc.student.lib.ByAgeRequest request, StreamObserver<StudentByAgeResponse> responseObserver) {
        responseObserver.onNext(StudentByAgeResponse.newBuilder().setAge(request.getAge()).setName("李四").build());
        responseObserver.onNext(StudentByAgeResponse.newBuilder().setAge("22").build());

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        responseObserver.onNext(StudentByAgeResponse.newBuilder().setName("战三").build());
        responseObserver.onNext(StudentByAgeResponse.newBuilder().setAge("25").build());
        responseObserver.onCompleted();
    }

    /**
     * 单向客户端端流式请求
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<com.zync.grpc.student.lib.ByAgeRequest> getStudentByAge(StreamObserver<com.zync.grpc.student.lib.StudentListResponse> responseObserver) {
        return new StreamObserver<com.zync.grpc.student.lib.ByAgeRequest>() {
            //请求是流式  请求一次  返回一次
            @Override
            public void onNext(com.zync.grpc.student.lib.ByAgeRequest byAgeRequest) {
                log.info("Server => " + byAgeRequest.getAge());
            }

            @Override
            public void onError(Throwable throwable) {

            }
            @Override
            public void onCompleted() {
                StudentByAgeResponse response = StudentByAgeResponse.newBuilder().setAge("22").setName("张三").build();
                StudentByAgeResponse response1 = StudentByAgeResponse.newBuilder().setAge("23").setName("王五").build();
                StudentByAgeResponse respons2 = StudentByAgeResponse.newBuilder().setAge("24").setName("赵6").build();
                com.zync.grpc.student.lib.StudentListResponse listResponse= com.zync.grpc.student.lib.StudentListResponse.newBuilder().addResponse(response).addResponse(response1).addResponse(respons2).build();
                responseObserver.onNext(listResponse);
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * 双向流式请求和响应
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<com.zync.grpc.student.lib.StreamRequest> getStudentByBItalk(StreamObserver<com.zync.grpc.student.lib.StreamResponse> responseObserver) {
        return new StreamObserver<com.zync.grpc.student.lib.StreamRequest>() {
            @Override
            public void onNext(com.zync.grpc.student.lib.StreamRequest streamRequest) {
                log.info(streamRequest.getRequestInfo());
                responseObserver.onNext(com.zync.grpc.student.lib.StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID()+"").build());
                responseObserver.onNext(com.zync.grpc.student.lib.StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID()+"").build());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("双向流式请求和响应异常", throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
