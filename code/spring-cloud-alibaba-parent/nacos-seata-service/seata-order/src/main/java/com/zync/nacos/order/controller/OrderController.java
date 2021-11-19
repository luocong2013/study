package com.zync.nacos.order.controller;

import com.zync.nacos.order.pojo.Order;
import com.zync.nacos.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单服务控制器
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/17 17:13
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 下单
     * @param userId 用户ID
     * @param productId 产品ID
     * @param amount 购买数量
     * @param version 版本
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Order> create(@RequestParam("userId") String userId,
                                        @RequestParam("productId") Long productId,
                                        @RequestParam("amount") Long amount,
                                        @RequestParam(value = "version", required = false) String version) {
        log.info("[order create] 收到下单请求, 用户:{}, 产品ID:{}, 购买数量:{}, 版本:{}.", userId, productId, amount, version);
        Order order;
        if (StringUtils.isBlank(version)) {
            order = orderService.create(userId, productId, amount);
        } else {
            order = orderService.createV2(userId, productId, amount);
        }
        return ResponseEntity.ok(order);
    }

}
