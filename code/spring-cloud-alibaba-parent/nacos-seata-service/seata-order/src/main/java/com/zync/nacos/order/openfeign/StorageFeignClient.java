package com.zync.nacos.order.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 库存Feign
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/17 17:09
 */
@FeignClient(value = "seata-storage")
public interface StorageFeignClient {

    /**
     * 扣减库存
     * @param id
     * @param amount
     * @return
     */
    @PostMapping("/storage/deduct")
    ResponseEntity<String> deduct(@RequestParam("id") Long id, @RequestParam("amount") Long amount);

    /**
     * 获取库存产品单价
     * @param id
     * @return
     */
    @GetMapping("/storage/info/price")
    ResponseEntity<Long> infoPrice(@RequestParam("id") Long id);
}
