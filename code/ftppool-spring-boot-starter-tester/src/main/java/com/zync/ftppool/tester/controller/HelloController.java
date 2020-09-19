package com.zync.ftppool.tester.controller;

import com.zync.boot.ftppool.client.FtpClientExt;
import com.zync.boot.ftppool.service.FtpClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption hello
 * @date 2020/9/19 22:43
 */
@Slf4j
@RestController
public class HelloController {


    @Autowired
    private FtpClientService ftpClientService;


    @GetMapping("/listFtp")
    public List<String> listFtp() {
        try {
            FtpClientExt ftpClient = ftpClientService.getFtpClient();
            try {
                FTPFile[] ftpFiles = ftpClient.listFiles();
                return Arrays.stream(ftpFiles).map(FTPFile::getName).collect(Collectors.toList());
            } catch (IOException e) {
                log.error("获取文件异常", e);
            } finally {
                ftpClientService.releaseFtpClient(ftpClient);
            }
        } catch (Exception e) {
            log.error("获取数据异常", e);
        }
        return Collections.emptyList();
    }
}
