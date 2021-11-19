package com.zync.nacos.account.service;

import com.zync.nacos.account.dao.AccountDao;
import com.zync.nacos.account.pojo.Account;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * 账户服务Service
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/17 16:45
 */
@Slf4j
@Service
public class AccountService {

    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * 扣减余额
     * @param userId
     * @param money
     */
    @Transactional(rollbackFor = Exception.class)
    public void deduct(String userId, Long money) {
        log.info("[account deduct] 当前 XID: {}.", RootContext.getXID());

        // 检查余额
        checkMoney(userId, money);
        // 扣减余额
        int modify = accountDao.modifyMoneyById(money, userId);
        Assert.isTrue(modify > 0, "扣减余额失败.");

        log.info("[account deduct] 扣减用户 {} 余额成功.", userId);
    }

    /**
     * 检查余额
     * @param userId
     * @param money
     */
    private void checkMoney(String userId, Long money) {
        log.info("[account checkMoney] 检查用户 {} 余额.", userId);
        Account account = accountDao.findByUserId(userId);
        Assert.notNull(account, "无此用户.");
        Long balance = Optional.ofNullable(account.getMoney()).orElse(0L);
        if (balance < money) {
            log.warn("[account checkMoney] 用户 {} 余额不足, 扣减金额:{}, 当前余额:{}.", userId, money, balance);
            throw new RuntimeException("余额不足");
        }
    }
}
