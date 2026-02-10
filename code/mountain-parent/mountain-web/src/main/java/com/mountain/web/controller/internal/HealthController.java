package com.mountain.web.controller.internal;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.mountain.common.base.ApiResponseEntity;
import com.mountain.common.configuration.mybatis.SnowflakeIdWorker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * health
 *
 * @author luocong
 * @version v1.0
 * @since 2023/11/17 14:30t
 */
@RestController
@RequestMapping("/internal")
public class HealthController {

    @GetMapping("/health")
    public ApiResponseEntity<Object> health() {
        return ApiResponseEntity.success();
    }

    @GetMapping("/genId")
    public ApiResponseEntity<Object> genId() {
        return ApiResponseEntity.success(IdWorker.getId());
    }

    @GetMapping("/parse/{id}")
    public ApiResponseEntity<Object> parseIdTimestamp(@PathVariable("id") Long id) {
        return ApiResponseEntity.success(SnowflakeIdWorker.parseIdTimestamp(id));
    }

}
