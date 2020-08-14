package com.redis.redisdemo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author luocong
 * @description redis配置文件
 * @date 2020/5/22 17:27
 */
@SpringBootConfiguration
public class RedisConfig {

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public <K, V> RedisTemplate<K, V> redisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 第②种限流方式 RedisScript
     * @return
     */
    //@Bean
    //public RedisScript<Boolean> redisScript() {
    //    DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
    //    redisScript.setLocation(new ClassPathResource("/lua/TimeRateLimit.lua"));
    //    redisScript.setResultType(Boolean.class);
    //    return redisScript;
    //}


    /**
     * 第③种限流方式 RedisScript
     * @return
     */
    //@Bean
    //public RedisScript<Number> redisScript() {
    //    DefaultRedisScript<Number> redisScript = new DefaultRedisScript<>();
    //    // 读取 lua 脚本
    //    redisScript.setLocation(new ClassPathResource("/lua/TokenBucketRateLimit.lua"));
    //    redisScript.setResultType(Number.class);
    //    return redisScript;
    //}

    /**
     * 第④种限流方式 RedisScript
     * @return
     */
    @Bean
    public RedisScript<Boolean> redisScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        // 读取 lua 脚本
        redisScript.setLocation(new ClassPathResource("/lua/TokenBucketRateLimit3.lua"));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
}
