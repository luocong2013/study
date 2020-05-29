package com.redis.redisdemo.aspect;

import com.google.common.collect.ImmutableList;
import com.redis.redisdemo.annotation.Limit;
import com.redis.redisdemo.common.LimitType;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author luocong
 * @description 限流切面实现
 * @date 2020/5/28 17:54
 */
@Aspect
@Component
public class LimitAspect {

    private static final Logger logger = LoggerFactory.getLogger(LimitAspect.class);

    private static final String UNKNOWN = "unknown";

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Pointcut(value = "@annotation(com.redis.redisdemo.annotation.Limit)")
    public void pointcut() {
    }

    @Around(value = "pointcut() && @annotation(limit)", argNames = "pjp, limit")
    public Object around(ProceedingJoinPoint pjp, Limit limit) {
        LimitType limitType = limit.limitType();
        String name = limit.name();
        int limitPeriod = limit.period();
        int limitCount = limit.count();
        /**
         * 根据限流类型获取不同的key ,如果不传我们会以方法名作为key
         */
        String key;
        switch (limitType) {
            case IP:
                key = getIpAddress();
                break;
            case CUSTOMER:
                key = limit.key();
                break;
            default:
                key = StringUtils.upperCase(limit.name());
        }

        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(limit.prefix(), key));
        try {
            String luaScript = buildLuaScript();
            RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
            Number count = redisTemplate.execute(redisScript, keys, limitCount, limitPeriod);
            logger.info("Access try count is {} for name is {} and key is {}", count, name, key);
            if (count != null && count.intValue() <= limitCount) {
                return pjp.proceed();
            } else {
                throw new RuntimeException("You have been dragged into the blacklist");
            }
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            throw new RuntimeException("server exception");
        }
    }

    /**
     * redis Lua 限流脚本
     *
     * @return
     */
    public String buildLuaScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local count = redis.call('get', KEYS[1])");
        // 超过最大值，则直接返回
        lua.append("\nif count and tonumber(count) > tonumber(ARGV[1]) then");
        lua.append("\n  return count");
        lua.append("\nend");
        // 执行计算器自加
        lua.append("\ncount = redis.call('incr', KEYS[1])");
        lua.append("\nif tonumber(count) == 1 then");
        // 从第一次调用开始限流，设置对应键值的过期
        lua.append("\n  redis.call('expire', KEYS[1], ARGV[2])");
        lua.append("\nend");
        lua.append("\nreturn count");
        return lua.toString();
    }


    /**
     * 获取ip地址
     *
     * @return
     */
    public String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
