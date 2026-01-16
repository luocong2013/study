package com.customzied.server.controller.internal;

import com.customzied.common.common.ApiResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
