package com.zync.grpc.server.service;

import com.google.protobuf.ByteString;
import com.zync.grpc.ansible.lib.PlayBookResponse;
import com.zync.grpc.ansible.lib.PlaybookGrpc;
import com.zync.grpc.ansible.lib.PlaybookRequest;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.zync.grpc.ansible.lib.PlayBookResponse.Status.END_EXECUTE;
import static com.zync.grpc.ansible.lib.PlayBookResponse.Status.IN_EXECUTE;
import static com.zync.grpc.ansible.lib.PlaybookRequest.TaskType.COMMAND;
import static com.zync.grpc.ansible.lib.PlaybookRequest.TaskType.PLAYBOOK;

/**
 * playbook grpc 服务
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/20 11:53
 */
@Slf4j
@GrpcService
public class PlaybookServerService extends PlaybookGrpc.PlaybookImplBase {

    @Override
    public void executePlaybook(PlaybookRequest request, StreamObserver<PlayBookResponse> responseObserver) {
        log.info("playbook grpc server start execute.");
        PlaybookRequest.TaskType type = request.getType();
        long taskId = request.getTaskId();
        if (Objects.equals(PLAYBOOK, type)) {
            // playbook 任务
            executePlaybook(taskId, request, responseObserver);
        } else if (Objects.equals(COMMAND, type)) {

        }

        log.info("playbook grpc server end execute.");
    }

    /**
     * 执行playbook任务
     * @param taskId
     * @param request
     * @param responseObserver
     */
    private void executePlaybook(long taskId, PlaybookRequest request, StreamObserver<PlayBookResponse> responseObserver) {
        log.info("start execute playbook task, task id is {}.", taskId);
        ByteString playbook = request.getPlaybook();
        try (OutputStream outputStream = new FileOutputStream("/Users/xylink/Downloads/grpcServer/data-" + taskId + ".zip")) {
            // 保存文件
            playbook.writeTo(outputStream);

            executeMessage(responseObserver, taskId, IN_EXECUTE, "执行Playbook日志信息1", 10);
            executeMessage(responseObserver, taskId, IN_EXECUTE, "执行Playbook日志信息2", 10);
            executeMessage(responseObserver, taskId, IN_EXECUTE, "执行Playbook日志信息3", 20);
            executeMessage(responseObserver, taskId, END_EXECUTE, "执行Playbook日志信息4", 0);
            responseObserver.onCompleted();
        } catch (IOException e) {
            log.error("读取文件异常", e);
        }
        log.info("end execute playbook task, task id is {}.", taskId);
    }

    /**
     * 执行脚本任务
     * @param taskId
     * @param request
     * @param responseObserver
     */
    private void executeCommand(long taskId, PlaybookRequest request, StreamObserver<PlayBookResponse> responseObserver) {
        log.info("start execute command task, task id is {}.", taskId);
        String command = request.getCommand();
        log.info("command: {}", command);

        executeMessage(responseObserver, taskId, IN_EXECUTE, "执行Command日志信息1", 10);
        executeMessage(responseObserver, taskId, IN_EXECUTE, "执行Command日志信息2", 10);
        executeMessage(responseObserver, taskId, IN_EXECUTE, "执行Command日志信息3", 20);
        executeMessage(responseObserver, taskId, END_EXECUTE, "执行Command日志信息4", 0);
        responseObserver.onCompleted();
        log.info("end execute command task, task id is {}.", taskId);
    }

    /**
     * 执行消息
     * @param responseObserver
     * @param taskId
     * @param status
     * @param output
     * @param timeout
     */
    private void executeMessage(StreamObserver<PlayBookResponse> responseObserver, long taskId, PlayBookResponse.Status status, String output, long timeout) {
        responseObserver.onNext(PlayBookResponse.newBuilder().setTaskId(taskId).setStatus(status).setOutput(output).build());
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            log.error("线程被中断", e);
            Thread.currentThread().interrupt();
        }
    }
}
