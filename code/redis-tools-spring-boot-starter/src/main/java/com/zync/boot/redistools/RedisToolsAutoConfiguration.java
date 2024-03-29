package com.zync.boot.redistools;

import com.zync.boot.redistools.annotation.DistributedLock;
import com.zync.boot.redistools.aop.CoreAnnotationAdvisor;
import com.zync.boot.redistools.aop.DistributedLockAnnotationInterceptor;
import com.zync.boot.redistools.aspect.DistributedLimitAspect;
import org.springframework.aop.Advisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author luocong
 * @version V1.0.0
 * @descrption redis tools 自动配置类
 * @date 2020/9/19 14:09
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisTemplate.class)
public class RedisToolsAutoConfiguration {

    @Bean
    @Order(1)
    @ConditionalOnMissingBean(value = DistributedLimitAspect.class)
    @ConditionalOnProperty(prefix = "redis.tools.limit", value = "enabled", havingValue = "true", matchIfMissing = true)
    public DistributedLimitAspect distributedLimitAspect() {
        return new DistributedLimitAspect();
    }

    //@Bean
    //@Order(2)
    //@ConditionalOnMissingBean(value = DistributedLockAspect.class)
    //@ConditionalOnProperty(prefix = "redis.tools.lock", value = "enabled", havingValue = "true", matchIfMissing = true)
    //public DistributedLockAspect distributedLockAspect() {
    //    return new DistributedLockAspect();
    //}

    @Bean
    @ConditionalOnProperty(prefix = "redis.tools.lock", value = "enabled", havingValue = "true", matchIfMissing = true)
    public Advisor distributedLockAnnotationAdvisor(RedisTemplate<String, String> redisTemplate) {
        DistributedLockAnnotationInterceptor interceptor = new DistributedLockAnnotationInterceptor(redisTemplate);
        return new CoreAnnotationAdvisor(interceptor, DistributedLock.class);
    }
}
