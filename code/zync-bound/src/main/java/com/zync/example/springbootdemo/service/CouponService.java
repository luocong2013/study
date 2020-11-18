package com.zync.example.springbootdemo.service;

import com.zync.example.springbootdemo.event.UserRegisterEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description 优惠卷服务
 * @date 2020/11/18 14:51
 */
@Service
public class CouponService {

    @EventListener(UserRegisterEvent.class)
    public void addCoupon(UserRegisterEvent event) {
        System.out.println(String.format("用户[%s]注册，优惠卷服务，增送优惠卷...", event.getUsername()));
    }
}
