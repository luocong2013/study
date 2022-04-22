package com.zync.emqx.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Emqx 规则引擎资源
 *
 * @author luocong
 * @version v1.0
 * @date 2022/4/22 23:20
 */
@Slf4j
@RestController
@RequestMapping("/resource")
public class RuleController {

    @RequestMapping("/process")
    public void process(@RequestBody Map<String,Object> params){
        log.info("规则引擎--------");
        params.forEach((key, value) -> System.out.println(key + ":" + value));
    }
}
