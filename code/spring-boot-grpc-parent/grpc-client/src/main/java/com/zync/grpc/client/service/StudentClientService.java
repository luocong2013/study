package com.zync.grpc.client.service;

import com.zync.grpc.student.lib.ByAgeRequest;
import com.zync.grpc.student.lib.StudentByAgeResponse;
import com.zync.grpc.student.lib.StudentListResponse;
import com.zync.grpc.student.lib.StudentRequest;
import com.zync.grpc.student.lib.StudentResponse;
import com.zync.grpc.student.lib.StudentServiceGrpc.StudentServiceBlockingStub;
import com.zync.grpc.student.lib.StudentServiceGrpc.StudentServiceStub;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * student grpc 客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/20 13:08
 */
@Service
public class StudentClientService {

    @GrpcClient("local-grpc-server")
    private StudentServiceBlockingStub studentRpcServiceBlockingStub;

    @GrpcClient("local-grpc-server")
    private StudentServiceStub studentRpcServiceStub;


    public String getRealName(String name) {
        StudentResponse response = studentRpcServiceBlockingStub.getRealName(StudentRequest.newBuilder().setName(name).build());
        return response.getRealName();
    }



    public void getRealByAge(String age) {
        Iterator<StudentByAgeResponse> response = studentRpcServiceBlockingStub.getRealByAge(ByAgeRequest.newBuilder().setAge(age).build());
        while (response.hasNext()){
            StudentByAgeResponse response1= response.next();
            System.out.println(response1.getAge());
            System.out.println(response1.getName());
        }
    }


    public void getStudentByAge(String age) {
        StreamObserver<StudentListResponse> streamObserver=  new StreamObserver<StudentListResponse>() {
            @Override
            public void onNext(StudentListResponse value) {
                value.getResponseList().forEach(e->{
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
        StreamObserver<ByAgeRequest> response = studentRpcServiceStub.getStudentByAge(streamObserver);
        response.onNext(ByAgeRequest.newBuilder().setAge("22").build());
        response.onNext(ByAgeRequest.newBuilder().setAge("23").build());
        response.onNext(ByAgeRequest.newBuilder().setAge("24").build());
        response.onNext(ByAgeRequest.newBuilder().setAge("25").build());
        response.onNext(ByAgeRequest.newBuilder().setAge("26").build());
        response.onCompleted();
    }
}
