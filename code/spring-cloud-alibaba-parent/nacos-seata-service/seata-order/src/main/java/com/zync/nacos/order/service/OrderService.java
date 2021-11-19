package com.zync.nacos.order.service;

import com.zync.nacos.order.dao.OrderDao;
import com.zync.nacos.order.http.OrderHttpExecutor;
import com.zync.nacos.order.openfeign.AccountFeignClient;
import com.zync.nacos.order.openfeign.StorageFeignClient;
import com.zync.nacos.order.pojo.Order;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * 订单服务Service
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/17 17:17
 */
@Slf4j
@Service
public class OrderService {

    private final StorageFeignClient storageFeignClient;
    private final AccountFeignClient accountFeignClient;
    private final OrderDao orderDao;
    private final OrderHttpExecutor httpExecutor;

    public OrderService(StorageFeignClient storageFeignClient, AccountFeignClient accountFeignClient,
                        OrderDao orderDao, OrderHttpExecutor httpExecutor) {
        this.storageFeignClient = storageFeignClient;
        this.accountFeignClient = accountFeignClient;
        this.orderDao = orderDao;
        this.httpExecutor = httpExecutor;
    }

    /**
     * 下单
     * @param userId
     * @param productId
     * @param amount
     * @return
     */
    @GlobalTransactional(timeoutMills = 600000)
    public Order create(String userId, Long productId, Long amount) {
        log.info("[order create] 当前 XID: {}", RootContext.getXID());

        // 1. 扣减库存
        log.info("扣减库存开始...");
        ResponseEntity<String> response = storageFeignClient.deduct(productId, amount);
        log.info("扣减库存返回结果: {}", response.getBody());
        log.info("扣减库存结束...");

        // 2. 扣减余额
        log.info("扣减余额开始...");
        long price = Optional.ofNullable(storageFeignClient.infoPrice(productId).getBody()).orElse(0L);
        response = accountFeignClient.deduct(userId, price * amount);
        log.info("扣减余额返回结果: {}", response.getBody());
        log.info("扣减余额结束...");

        // 3. 创建订单
        log.info("创建订单开始...");
        Order order = new Order(productId, amount, userId, new Date(), 2);
        return orderDao.save(order);
    }

    /**
     * 下单V2（通过Http）
     * @param userId
     * @param productId
     * @param amount
     * @return
     */
    @GlobalTransactional
    public Order createV2(String userId, Long productId, Long amount) {
        log.info("[order create] 当前 XID: {}", RootContext.getXID());

        // 1. 扣减库存
        log.info("扣减库存开始...");
        String response = httpExecutor.reduceStock(productId, amount);
        log.info("扣减库存返回结果: {}", response);
        log.info("扣减库存结束...");

        // 2. 扣减余额
        log.info("扣减余额开始...");
        long price = Optional.ofNullable(storageFeignClient.infoPrice(productId).getBody()).orElse(0L);
        response = httpExecutor.reduceBalance(userId, price * amount);
        log.info("扣减余额返回结果: {}", response);
        log.info("扣减余额结束...");

        // 3. 创建订单
        log.info("创建订单开始...");
        Order order = new Order(productId, amount, userId, new Date(), 2);
        return orderDao.save(order);
    }
}
