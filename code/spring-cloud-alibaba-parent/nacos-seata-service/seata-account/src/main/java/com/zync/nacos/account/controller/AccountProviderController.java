package com.zync.nacos.account.controller;

import com.zync.nacos.account.dto.AccountDTO;
import com.zync.nacos.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * 账户服务控制器
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/17 16:34
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountProviderController {

    private final AccountService accountService;

    public AccountProviderController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 扣减余额
     * @param userId 用户ID
     * @param money 金额
     * @return
     */
    @PostMapping("/deduct")
    public ResponseEntity<String> deduct(@RequestParam("userId") String userId, @RequestParam("money") Long money) {
        log.info("[account deduct] 收到扣减余额请求, 用户ID:{}, 金额:{}.", userId, money);
        accountService.deduct(userId, money);
        return ResponseEntity.ok("扣减余额成功");
    }

    /**
     * 扣减金额V2
     * @param account
     * @return
     */
    @PostMapping("/deductV2")
    public ResponseEntity<String> deduct(@RequestBody @Valid AccountDTO account) {
        log.info("[account deduct] 收到扣减余额请求, 用户ID:{}, 金额:{}.", account.getUserId(), account.getMoney());
        accountService.deduct(account.getUserId(), account.getMoney());
        return ResponseEntity.ok("扣减余额成功");
    }
}
