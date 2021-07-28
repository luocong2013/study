package com.zync.ibed.controller;

import com.zync.ibed.common.ResultApi;
import com.zync.ibed.common.ResultApiFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 图床控制器
 * @date 2020/4/19 20:01
 */
@RestController
public class IbedController {

    @PostMapping("/upload")
    public ResponseEntity<ResultApi> upload() {
        //System.out.println(files.length);
        return ResponseEntity.ok(ResultApiFactory.buildSuccessResult("成功"));
    }
}
