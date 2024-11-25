package com.zync.controller;

import com.zync.http.api.DemoApi;
import com.zync.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo Controller
 *
 * @author luocong
 * @version v1.0
 * @date 2022/12/12 20:19
 */
@RestController
public class DemoController {

    private final DemoService demoService;

    private final DemoApi demoApi;

    public DemoController(DemoService demoService, DemoApi demoApi) {
        this.demoService = demoService;
        this.demoApi = demoApi;
    }

    @GetMapping("/hi/{name}")
    public String hi(@PathVariable("name") String name) {
        return demoService.hi(name);
    }

    @GetMapping("/weather/{code}")
    public String weather(@PathVariable("code") String code) {
        return demoApi.weather(code);
    }
}
