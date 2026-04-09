package com.ysyue.im.listener;

import com.ysyue.im.server.ImServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * IM Server 启动触发器
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/24 16:42
 **/
@Component
@RequiredArgsConstructor
public class ImServerStartListener {

    private final ImServer imServer;

    @EventListener(ApplicationReadyEvent.class)
    public void start() throws InterruptedException {
        imServer.start();
    }

}
