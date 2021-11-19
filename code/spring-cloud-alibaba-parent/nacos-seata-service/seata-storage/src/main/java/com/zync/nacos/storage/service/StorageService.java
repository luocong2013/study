package com.zync.nacos.storage.service;

import com.zync.nacos.storage.dao.StorageDao;
import com.zync.nacos.storage.pojo.Storage;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * 库存服务Service
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/17 11:08
 */
@Slf4j
@Service
public class StorageService {

    private final StorageDao storageDao;

    public StorageService(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    /**
     * 扣减库存
     * @param id 产品ID
     * @param amount 购买数量
     */
    @Transactional(rollbackFor = Exception.class)
    public void deduct(Long id, Long amount) {
        log.info("[storage deduct] 当前 XID: {}.", RootContext.getXID());

        // 检查库存
        checkStock(id, amount);
        // 扣减库存
        int modify = storageDao.modifyNumById(amount, id);
        Assert.isTrue(modify > 0, "扣减库存失败.");

        log.info("[storage deduct] 扣减产品ID: {} 库存成功.", id);
    }

    /**
     * 获取库存产品信息
     * @param id 产品ID
     * @return
     */
    public Storage info(Long id) {
        Optional<Storage> optional = storageDao.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("无此库存数据.");
        }
        return optional.get();
    }

    /**
     * 检查库存
     * @param id 产品ID
     * @param amount 购买数量
     */
    private void checkStock(Long id, Long amount) {
        Storage storage = info(id);
        Long num = Optional.ofNullable(storage.getNum()).orElse(0L);
        if (num < amount) {
            log.warn("[storage checkStock] {} 库存不足, 购买数量: {}, 当前库存: {}.", id, amount, num);
            throw new RuntimeException("库存不足.");
        }
    }

}
