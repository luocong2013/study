package com.zync.grpc.server2;

import com.zync.grpc.server2.service.StudentServerService2;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;

import java.io.IOException;

/**
 * Grpc 服务端2 main 启动
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/20 11:37
 */
public class GrpcServer2 {

    private Server server;

    public void start() throws IOException {
        NettyServerBuilder serverBuilder = NettyServerBuilder.forPort(8002);
        //将所有的远程服务类加进去管理
        serverBuilder.addService(new StudentServerService2());
        //我这里只有一个实现类

        this.server = serverBuilder.build();
        this.server.start();
    }

    public void stop() {
        if (null != this.server) {
            this.server.shutdown();
        }
    }

    /**
     * 阻塞等待RPC服务器停止
     */
    public void blockUntilShutdown() {
        if (server != null) {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        GrpcServer2 grpcServer = new GrpcServer2();
        grpcServer.start();
        grpcServer.blockUntilShutdown();
    }

}
