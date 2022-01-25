package com.zync.logback.trace.controller;

import com.zync.logback.trace.common.Const;
import com.zync.logback.trace.service.LogbackTraceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * logback trace控制器
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/24 11:48
 */
@Slf4j
@RestController
public class LogbackTraceController {

    private final LogbackTraceService logbackTraceService;

    public LogbackTraceController(LogbackTraceService logbackTraceService) {
        this.logbackTraceService = logbackTraceService;
    }

    @GetMapping("/error")
    public ResponseEntity<String> error() {
        try {
            throw new RuntimeException("Runtime Exception.");
        } catch (RuntimeException e) {
            log.error("error信息", e);
        }
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    @GetMapping("/warn")
    public ResponseEntity<String> warn() {
        log.warn("warn信息");
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    @GetMapping("/info")
    public ResponseEntity<String> info() {
        log.info("info信息");
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    @GetMapping("/debug")
    public ResponseEntity<String> debug() {
        log.debug("debug信息");
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    @GetMapping("/trace")
    public ResponseEntity<String> trace() {
        log.trace("trace信息");
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    @GetMapping("/httpClient")
    public ResponseEntity<String> httpClient(@RequestParam(value = "httpUrl", defaultValue = Const.HTTP_URL) String httpUrl) {
        log.info("Controller: httpClient执行GET请求, httpUrl: [{}]", httpUrl);
        return ResponseEntity.ok(logbackTraceService.httpClient(httpUrl));
    }

    @GetMapping("/restTemplate")
    public ResponseEntity<String> restTemplate(@RequestParam(value = "httpUrl", defaultValue = Const.HTTP_URL) String httpUrl) {
        log.info("Controller: restTemplate执行GET请求, httpUrl: [{}]", httpUrl);
        return ResponseEntity.ok(logbackTraceService.restTemplate(httpUrl));
    }

    @GetMapping("/okHttp")
    public ResponseEntity<String> okHttp(@RequestParam(value = "httpUrl", defaultValue = Const.HTTP_URL) String httpUrl) {
        log.info("Controller: okHttp执行GET请求, httpUrl: [{}]", httpUrl);
        return ResponseEntity.ok(logbackTraceService.okHttp(httpUrl));
    }

    @GetMapping("/threadPool")
    public ResponseEntity<String> threadPool(@RequestParam(value = "httpUrl", defaultValue = Const.HTTP_URL) String httpUrl) {
        log.info("Controller: 使用线程池的okHttp执行GET请求, httpUrl: [{}]", httpUrl);
        logbackTraceService.threadPool(httpUrl);
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

}
