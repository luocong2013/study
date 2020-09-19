package com.zync.boot.redistools;

import com.zync.boot.redistools.aspect.DistributedLockAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption redis tools 自动配置类
 * @date 2020/9/19 14:09
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisTemplate.class)
public class RedisToolsAutoConfiguration {

    @Bean
    @Order(2)
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "redis.tools", value = "enabled", havingValue = "true")
    public DistributedLockAspect distributedLockAspect() {
        return new DistributedLockAspect();
    }
}
