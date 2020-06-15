package com.zync.example.springbootdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luocong
 * @description 日志控制器
 * @date 2020/5/25 11:25
 */
@RestController
public class LogController {

    @GetMapping("/log/{start}")
    public String log(@PathVariable String start) {
        return "log " + start;
    }

    @GetMapping("/log2")
    public String log2() {
        int i = 1 / 0;
        return "log2";
    }
}
