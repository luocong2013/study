package com.zync.example.springbootdemo.masync.controller;

import com.zync.example.springbootdemo.masync.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 01396453(luocong)
 * @version 1.0.0
 * @description 方法异步执行控制器
 * @date 2020/8/3 15:58
 */
@RestController
@RequestMapping("/masync")
public class MAsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/async")
    public String async() {
        System.out.println("MAsyncController: " + Thread.currentThread().getName());
        return asyncService.async();
    }
}
