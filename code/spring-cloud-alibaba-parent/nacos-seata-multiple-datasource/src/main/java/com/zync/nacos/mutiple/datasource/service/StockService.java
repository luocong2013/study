package com.zync.nacos.mutiple.datasource.service;

/**
 * @author luocong
 */
public interface StockService {
    /**
     * 扣减库存
     *
     * @param productId 商品 ID
     * @param amount    扣减数量
     * @return 操作结果
     */
    boolean reduceStock(Long productId, Integer amount);
}
