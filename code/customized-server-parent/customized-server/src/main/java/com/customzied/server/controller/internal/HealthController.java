package com.customzied.server.controller.internal;

import com.customzied.common.exception.BaseMessage;
import com.customzied.common.exception.BaseResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * health
 *
 * @author luocong
 * @version v1.0
 * @date 2023/11/17 14:30
 */
@RestController
@RequestMapping("/internal")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<BaseMessage> health() {
        return BaseResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

}
