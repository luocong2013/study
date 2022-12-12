package com.zync.controller;

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

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/hi/{name}")
    public String hi(@PathVariable("name") String name) {
        return demoService.hi(name);
    }
}
