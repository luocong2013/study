package com.zync.oauth2.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Index 控制器
 *
 * @author luocong
 * @version v1.0
 * @date 2022/2/15 16:20
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
