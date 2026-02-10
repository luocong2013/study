package com.mountain.common.controller.internal;

import com.mountain.common.base.ApiResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * health
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/10 14:43
 **/
@RestController
@RequestMapping("/internal")
public class HealthController {

    @GetMapping("/health")
    public ApiResponseEntity<Object> health() {
        return ApiResponseEntity.success();
    }

}
