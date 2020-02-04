package com.zync.ibed.controller;

import com.zync.ibed.bean.bo.MinioItem;
import com.zync.ibed.common.ResultApi;
import com.zync.ibed.common.ResultApiFactory;
import com.zync.ibed.minio.TiebaMinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 首页控制器
 * @date 2020/1/11 11:42
 */
@Slf4j
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private TiebaMinioService tiebaMinioService;

    /**
     * 加载数据
     * @param prefix 前缀
     * @return
     */
    @GetMapping("/loadData")
    public ResponseEntity<ResultApi> loadData(@RequestParam(value = "prefix", required = false) String prefix) {
        try {
            List<MinioItem> data = tiebaMinioService.listMinioItems(prefix, false);
            return ResponseEntity.ok(ResultApiFactory.buildSuccessResult(data));
        } catch (Exception e) {
            log.error("加载数据失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultApiFactory.buildFailResult("加载数据失败"));
        }
    }
}
