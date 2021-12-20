package com.zync.grpc.client.service;

import com.google.protobuf.ByteString;
import com.zync.grpc.ansible.lib.PlayBookResponse;
import com.zync.grpc.ansible.lib.PlaybookGrpc.PlaybookBlockingStub;
import com.zync.grpc.ansible.lib.PlaybookRequest;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import static com.zync.grpc.ansible.lib.PlaybookRequest.TaskType.PLAYBOOK;

/**
 * playbook grpc 客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/20 13:05
 */
@Slf4j
@Service
public class PlaybookClientService {

    @GrpcClient("local-grpc-server")
    private PlaybookBlockingStub playbookBlockingStub;

    @Async
    public void executePlaybook() {
        log.info("client开始执行：{}", Thread.currentThread().getName());
        try (InputStream inputStream = new FileInputStream("/Users/xylink/.playbook/1422371786754039808/playbook-1422371786754039808.zip")) {
            ByteString byteString = ByteString.readFrom(inputStream);
            PlaybookRequest request = PlaybookRequest.newBuilder().setType(PLAYBOOK).setTaskId(1422371786754039808L).setPlaybook(byteString).build();
            Iterator<PlayBookResponse> responseIterator = playbookBlockingStub.executePlaybook(request);

            while (responseIterator.hasNext()) {
                PlayBookResponse response = responseIterator.next();
                long taskId = response.getTaskId();
                PlayBookResponse.Status status = response.getStatus();
                String output = response.getOutput();
                log.info("taskId: {}, status: {}, output: {}", taskId, status, output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            log.info("client执行结束：{}", Thread.currentThread().getName());
        }
    }
}
