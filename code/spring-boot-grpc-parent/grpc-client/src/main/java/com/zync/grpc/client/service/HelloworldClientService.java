package com.zync.grpc.client.service;

import com.zync.grpc.helloworld.lib.HelloReply;
import com.zync.grpc.helloworld.lib.HelloRequest;
import com.zync.grpc.helloworld.lib.SimpleGrpc.SimpleBlockingStub;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * helloworld grpc 客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/20 13:03
 */
@Service
public class HelloworldClientService {

    @GrpcClient("local-grpc-server")
    private SimpleBlockingStub simpleBlockingStub;

    /**
     * 客户端发消息
     * @param name
     * @return
     */
    public String sendMessage(String name) {
        try {
            HelloReply response = simpleBlockingStub.sayHello(HelloRequest.newBuilder().setName(name).build());
            return response.getMessage();
        } catch (StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode();
        }
    }
}
