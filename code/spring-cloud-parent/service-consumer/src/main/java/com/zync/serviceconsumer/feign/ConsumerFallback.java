package com.zync.serviceconsumer.feign;

/**
 * @author luoc
 * @version V1.0.0
 * @description Consumer接口实现
 * @date 2019/3/19 22:29
 */
public class ConsumerFallback implements Consumer {

    @Override
    public String serviceUrl() {
        return "Feign客户端访问失败";
    }

    @Override
    public String index() {
        return "Feign客户端访问失败";
    }

    @Override
    public String hello(String name) {
        return "Feign客户端访问失败";
    }
}
