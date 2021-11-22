package com.zync.nacos.mutiple.datasource.service.impl;

import com.zync.nacos.mutiple.datasource.dao.ProductDao;
import com.zync.nacos.mutiple.datasource.service.StockService;
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
public class StockServiceImpl implements StockService {

    @Autowired
    private ProductDao productDao;

    /**
     * 事务传播特性设置为 REQUIRES_NEW 开启新的事务
     *
     * @param productId 商品 ID
     * @param amount    扣减数量
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean reduceStock(Long productId, Integer amount) {
        log.info("=============STOCK=================");
        log.info("当前 XID: {}", RootContext.getXID());

        // 检查库存
        checkStock(productId, amount);

        log.info("开始扣减 {} 库存", productId);
        // 扣减库存
        int record = productDao.reduceStock(productId, amount);
        Assert.isTrue(record > 0, String.format("扣减 %d 库存失败.", productId));
        log.info("扣减 {} 库存成功.", productId);
        return true;
    }

    private void checkStock(Long productId, Integer requiredAmount) {

        log.info("检查 {} 库存", productId);
        int stock = productDao.getStock(productId);

        if (stock < requiredAmount) {
            log.warn("{} 库存不足，当前库存:{}", productId, stock);
            throw new RuntimeException("库存不足");
        }
    }
}
