package com.zync.nacos.mutiple.datasource.controller;

import com.zync.nacos.mutiple.datasource.common.OperationResponse;
import com.zync.nacos.mutiple.datasource.common.order.PlaceOrderRequestVO;
import com.zync.nacos.mutiple.datasource.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author HelloWoodes
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    @ResponseBody
    public OperationResponse placeOrder(@RequestBody @Valid PlaceOrderRequestVO placeOrderRequest) {
        log.info("收到下单请求,用户:{}, 商品:{}", placeOrderRequest.getUserId(), placeOrderRequest.getProductId());
        return orderService.placeOrder(placeOrderRequest);
    }
}
