package com.zync.nacos.mutiple.datasource.service;

/**
 * @author luocong
 */
public interface PayService {
    /**
     * 扣减金额
     * @param userId 用户 ID
     * @param price  扣减金额
     * @return 返回操作结果
     */
    boolean reduceBalance(Long userId, Integer price);

}
