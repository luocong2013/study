package com.redis.redisdemo.controller;

import com.redis.redisdemo.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @description Redis 控制器
 * @date 2020/5/26 17:37
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/addValue/{key}/{value}")
    public ResponseEntity<String> addValue(@PathVariable String key, @PathVariable String value) {
        redisService.setValue(key, value);
        return ResponseEntity.ok("添加成功");
    }

    @GetMapping("/addValueEx/{key}/{value}")
    public ResponseEntity<String> addValueEx(@PathVariable String key, @PathVariable String value) {
        redisService.setValueEx(key, value, 10, TimeUnit.SECONDS);
        return ResponseEntity.ok("添加成功");
    }

    @GetMapping("/getValue/{key}")
    public ResponseEntity<String> getValue(@PathVariable String key) {
        String value = redisService.getValue(key);
        return ResponseEntity.ok(value);
    }

    @GetMapping("/addListValue/{key}/{value}")
    public ResponseEntity<String> addListValue(@PathVariable String key, @PathVariable String value) {
        redisService.setListValue(key, value);
        return ResponseEntity.ok("添加成功");
    }

    @GetMapping("/getListValue/{key}")
    public ResponseEntity<String> getListValue(@PathVariable String key) {
        List<String> values = redisService.getListValue(key);
        return ResponseEntity.ok(StringUtils.join(values, ','));
    }
}
