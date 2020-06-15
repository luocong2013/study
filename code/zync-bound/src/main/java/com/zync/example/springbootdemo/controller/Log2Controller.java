package com.zync.example.springbootdemo.controller;

import com.zync.example.springbootdemo.aspect.LogOper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luocong
 * @description
 * @date 2020/5/25 14:02
 */
@RestController
public class Log2Controller {

    @LogOper(message = "日志3消息")
    @GetMapping("/log3")
    public String log3() {
        return "log3";
    }
}
