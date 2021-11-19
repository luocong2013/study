package com.zync.nacos.storage.controller;

import com.zync.nacos.storage.dto.ProductDTO;
import com.zync.nacos.storage.pojo.Storage;
import com.zync.nacos.storage.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 库存服务控制器
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/17 11:04
 */
@Slf4j
@RestController
@RequestMapping("/storage")
public class StorageProviderController {

    private final StorageService storageService;

    public StorageProviderController(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * 扣减库存接口
     * @param id 产品ID
     * @param amount 购买数量
     * @return
     */
    @PostMapping("/deduct")
    public ResponseEntity<String> deduct(@RequestParam("id") Long id, @RequestParam("amount") Long amount) {
        log.info("[storage deduct] 收到扣减库存请求, 产品ID:{}, 购买数量:{}.", id, amount);
        storageService.deduct(id, amount);
        return ResponseEntity.ok("扣减库存成功");
    }

    /**
     * 扣减库存接口V2
     * @param product
     * @return
     */
    @PostMapping("/deductV2")
    public ResponseEntity<String> deductV2(@RequestBody @Valid ProductDTO product) {
        log.info("[storage deduct] 收到扣减库存请求, 产品ID:{}, 购买数量:{}.", product.getId(), product.getAmount());
        storageService.deduct(product.getId(), product.getAmount());
        return ResponseEntity.ok("扣减库存成功");
    }

    /**
     * 获取产品单价接口
     * @param id 产品ID
     * @return
     */
    @GetMapping("/info/price")
    public ResponseEntity<Long> infoPrice(@RequestParam("id") Long id) {
        Storage storage = storageService.info(id);
        return ResponseEntity.ok(storage.getPrice());
    }
}
