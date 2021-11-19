package com.zync.nacos.order.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 账户Feign
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/17 17:12
 */
@FeignClient(value = "seata-account")
public interface AccountFeignClient {

    /**
     * 扣减余额
     * @param userId
     * @param money
     * @return
     */
    @PostMapping("/account/deduct")
    ResponseEntity<String> deduct(@RequestParam("userId") String userId, @RequestParam("money") Long money);
}
