package com.zync.grpc.server.service;

import com.zync.grpc.helloworld.lib.HelloReply;
import com.zync.grpc.helloworld.lib.HelloRequest;
import com.zync.grpc.helloworld.lib.SimpleGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * helloworld grpc 服务
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/20 12:35
 */
@Slf4j
@GrpcService
public class HelloworldServerService extends SimpleGrpc.SimpleImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        log.info("helloworld grpc server start execute.");
        String name = request.getName();
        log.info("name: {}", name);
        responseObserver.onNext(HelloReply.newBuilder().setMessage("Hello ==> " + name).build());
        responseObserver.onCompleted();
        log.info("helloworld grpc server end execute.");
    }
}
