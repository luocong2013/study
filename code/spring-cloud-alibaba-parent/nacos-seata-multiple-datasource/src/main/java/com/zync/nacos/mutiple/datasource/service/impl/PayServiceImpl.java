package com.zync.nacos.mutiple.datasource.service.impl;

import com.zync.nacos.mutiple.datasource.dao.AccountDao;
import com.zync.nacos.mutiple.datasource.service.PayService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author luocong
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private AccountDao accountDao;

    /**
     * 事务传播特性设置为 REQUIRES_NEW 开启新的事务
     *
     * @param userId 用户 ID
     * @param price  扣减金额
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean reduceBalance(Long userId, Integer price) {
        log.info("=============PAY=================");
        log.info("当前 XID: {}", RootContext.getXID());

        checkBalance(userId, price);

        log.info("开始扣减用户 {} 余额", userId);
        int record = accountDao.reduceBalance(price);
        Assert.isTrue(record > 0, String.format("扣减用户 %d 余额失败.", userId));
        log.info("扣减用户 {} 余额成功.", userId);
        return true;
    }

    private void checkBalance(Long userId, Integer price) {
        log.info("检查用户 {} 余额", userId);
        int balance = accountDao.getBalance(userId);

        if (balance < price) {
            log.warn("用户 {} 余额不足，当前余额:{}", userId, balance);
            throw new RuntimeException("余额不足");
        }

    }

}
