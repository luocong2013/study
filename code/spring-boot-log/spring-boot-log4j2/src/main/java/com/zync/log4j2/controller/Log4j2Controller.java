package com.zync.log4j2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * log4j2控制器
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/25 15:08
 */
@Slf4j
@RestController
public class Log4j2Controller {

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

}
