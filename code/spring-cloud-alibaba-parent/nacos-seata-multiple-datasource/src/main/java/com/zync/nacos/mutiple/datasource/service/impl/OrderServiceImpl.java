package com.zync.nacos.mutiple.datasource.service.impl;

import com.zync.nacos.mutiple.datasource.common.OperationResponse;
import com.zync.nacos.mutiple.datasource.common.order.Order;
import com.zync.nacos.mutiple.datasource.common.order.OrderStatus;
import com.zync.nacos.mutiple.datasource.common.order.PlaceOrderRequestVO;
import com.zync.nacos.mutiple.datasource.config.DataSourceKey;
import com.zync.nacos.mutiple.datasource.config.DynamicDataSourceContextHolder;
import com.zync.nacos.mutiple.datasource.dao.OrderDao;
import com.zync.nacos.mutiple.datasource.service.OrderService;
import com.zync.nacos.mutiple.datasource.service.PayService;
import com.zync.nacos.mutiple.datasource.service.StockService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author luocong
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PayService payService;

    @Autowired
    private StockService stockService;

    @GlobalTransactional
    @Override
    public OperationResponse placeOrder(PlaceOrderRequestVO placeOrderRequest) {
        log.info("=============ORDER=================");
        DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.ORDER);
        log.info("当前 XID: {}", RootContext.getXID());

        Integer amount = 1;
        Integer price = placeOrderRequest.getPrice();

        Order order = Order.builder().userId(placeOrderRequest.getUserId()).productId(
            placeOrderRequest.getProductId()).status(OrderStatus.INIT).payAmount(price).build();

        int saveOrderRecord = orderDao.saveOrder(order);
        Assert.isTrue(saveOrderRecord > 0, "保存订单失败.");
        log.info("保存订单成功.");

        // 扣减库存
        DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.STOCK);
        boolean operationStockResult = stockService.reduceStock(placeOrderRequest.getProductId(), amount);

        // 扣减余额
        DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.PAY);
        boolean operationBalanceResult = payService.reduceBalance(placeOrderRequest.getUserId(), price);

        log.info("=============ORDER=================");
        DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.ORDER);

        int updateOrderRecord = orderDao.updateOrder(order.getId(), OrderStatus.SUCCESS);
        Assert.isTrue(updateOrderRecord > 0, String.format("更新订单 %d 状态失败.", order.getId()));
        log.info("更新订单 {} 状态成功.", order.getId());

        return OperationResponse.builder().success(operationStockResult && operationBalanceResult).build();
    }
}
