package com.zync.serviceconsumerhystrix.feign.factory;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption fallbackFactory
 * @date 2019/4/21 22:19
 */
public class HystrixFeignClientFallbackFactory implements FallbackFactory<HystrixFeignClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixFeignClientFallbackFactory.class);

    @Override
    public HystrixFeignClient create(Throwable throwable) {
        LOGGER.info("fallback; reason was: {}", throwable.getMessage());
        return (HystrixFeignClientWithFactory) name -> "Feign客户端访问失败";
    }
}
