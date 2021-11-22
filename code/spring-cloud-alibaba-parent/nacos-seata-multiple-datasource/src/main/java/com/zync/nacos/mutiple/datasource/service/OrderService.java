package com.zync.nacos.mutiple.datasource.service;


import com.zync.nacos.mutiple.datasource.common.OperationResponse;
import com.zync.nacos.mutiple.datasource.common.order.PlaceOrderRequestVO;

/**
 * @author luocong
 */
public interface OrderService {

    /**
     * 下单
     *
     * @param placeOrderRequest 请求参数
     * @return 下单结果
     */
    OperationResponse placeOrder(PlaceOrderRequestVO placeOrderRequest);
}
