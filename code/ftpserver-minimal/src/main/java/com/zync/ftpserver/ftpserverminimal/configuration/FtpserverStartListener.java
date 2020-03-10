package com.zync.ftpserver.ftpserverminimal.configuration;

import com.zync.ftpserver.ftpserverminimal.service.FtpserverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption ftp server启动触发器
 * @date 2020/3/10 21:10
 */
@Slf4j
@Component
public class FtpserverStartListener {

    @Autowired
    private FtpserverService ftpserverService;

    /**
     * 启动方法
     */
    @EventListener(ApplicationReadyEvent.class)
    public void listener() {
        try {
            ftpserverService.start();
            log.info("Embed ftp server started successfully.");
        } catch (Exception e) {
            log.error("ftp server start failed.", e);
        }
    }
}
