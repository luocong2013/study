package com.redis.redisdemo.initializer;

import com.google.common.collect.Lists;
import com.redis.redisdemo.common.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 01396453(luocong)
 * @version 1.0.0
 * @description Redis令牌桶限流初始化
 * @date 2020/7/9 10:36
 */
@Slf4j
//@Component
public class TokenBucketRetaLimitInitializer implements InitializingBean {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    private RedisScript<Number> redisScript;

    private static final AtomicBoolean init = new AtomicBoolean(false);

    @Override
    public void afterPropertiesSet() throws Exception {
        if (init.compareAndSet(false, true)) {
            initTokenBucket("Token_Bucket", 3, 1);
        }
    }

    /**
     * 初始化redis令牌桶
     * @param key redis key
     * @param maxPermits 桶内令牌最大数量
     * @param rate 令牌放置速度 (个/秒)
     */
    public void initTokenBucket(String key, int maxPermits, int rate) {
        Number number = redisTemplate.execute(redisScript, Lists.newArrayList(key), Const.INIT_TOKEN_BUCKET, maxPermits, rate);
        if (Objects.nonNull(number) && number.intValue() == 1) {
            log.info("Redis令牌桶初始化成功，key: {}, maxPermits: {}, rate: {}", key, maxPermits, rate);
        } else {
            log.error("Redis令牌桶初始化失败，key: {}, maxPermits: {}, rate: {}", key, maxPermits, rate);
        }
    }
}
