package com.mountain.common.holder;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存工具类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/21 14:55
 **/
@Component
@RequiredArgsConstructor
public class RedisCacheHolder {

    private final RedisTemplate<String, Object> redisTemplate;

    // ============================= Common (公共操作) ============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return 成功返回true
     */
    public Boolean expire(String key, long time) {
        return expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存的过期时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @param timeUnit 时间单位
     * @return 缓存的过期时间
     */
    public Boolean expire(String key, long time, TimeUnit timeUnit) {
        return redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 根据key获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效，返回-2代表键不存在
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(List.of(key));
            }
        }
    }

    // ============================ String (字符串) =============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    @SuppressWarnings("unchecked")
    public <V> V get(String key) {
        return key == null ? null : (V) redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public <V> void set(String key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public <V> void set(String key, V value, long time) {
        set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 带时间设置缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public <V> void set(String key, V value, long time, TimeUnit timeUnit) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
        } else {
            set(key, value);
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return 增加后的值
     */
    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return 减少后的值
     */
    public Long decr(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    // ================================ Hash (哈希) =================================

    /**
     * HashGet
     *
     * @param key     键 不能为null
     * @param hashKey 项 不能为null
     * @return 值
     */
    @SuppressWarnings("unchecked")
    public <HV> HV hGet(String key, String hashKey) {
        return (HV) redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public void hmSet(String key, Map<Object, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key     键
     * @param hashKey 项
     * @param hashValue   值
     */
    public <HV> void hSet(String key, String hashKey, HV hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * 删除hash表中的值
     *
     * @param key      键 不能为null
     * @param hashKeys 项 可以使多个 不能为null
     */
    public void hDel(String key, Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key     键 不能为null
     * @param hashKey 项 不能为null
     * @return true 存在 false不存在
     */
    public Boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    // ============================ Set (集合) =============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return 值集合
     */
    public Set<Object> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public Boolean sHasKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSet(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    public Long sGetSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    // =============================== List (列表) =================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始 0 是第一个元素
     * @param end   结束 -1代表所有值
     * @return 列表
     */
    public List<Object> lGet(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    public Long lGetListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 将值放入list缓存 (尾部)
     *
     * @param key   键
     * @param value 值
     */
    public <V> void lSet(String key, V value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值列表
     */
    public <V> void lSetAll(String key, List<V> value) {
        redisTemplate.opsForList().rightPushAll(key, value);
    }

    // ============================ ZSet (有序集合) =============================

    /**
     * 向ZSet中添加元素，并设置分数
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     * @return true 成功 false 失败
     */
    public <V> Boolean zsSet(String key, V value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 批量向ZSet中添加元素
     *
     * @param key    键
     * @param tuples 包含值和分数的TypedTuple集合
     * @return 成功添加的个数
     */
    public Long zsSetAll(String key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
        return redisTemplate.opsForZSet().add(key, tuples);
    }

    /**
     * 删除ZSet中的一个或多个元素
     *
     * @param key    键
     * @param values 值（可多个）
     * @return 成功删除的个数
     */
    public Long zsRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key   键
     * @param value 值
     * @param delta 要增加的分数（可以为负数表示减少）
     * @return 增加后的分数
     */
    public <V> Double zsIncrementScore(String key, V value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 获取指定元素的score值
     *
     * @param key   键
     * @param value 值
     * @return 分数
     */
    public <V> Double zsGetScore(String key, V value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 获取指定元素的排名（按分数从小到大排）
     *
     * @param key   键
     * @param value 值
     * @return 排名（0表示第一名），如果元素不存在返回null
     */
    public <V> Long zsGetRank(String key, V value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 获取指定元素的排名（按分数从大到小排）
     *
     * @param key   键
     * @param value 值
     * @return 排名（0表示第一名），如果元素不存在返回null
     */
    public <V> Long zsGetReverseRank(String key, V value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取ZSet的元素个数
     *
     * @param key 键
     * @return 元素个数
     */
    public Long zsGetSize(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取指定分数区间内的元素个数
     *
     * @param key 键
     * @param min 最小分数
     * @param max 最大分数
     * @return 元素个数
     */
    public Long zsCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取指定排名区间内的元素集合（按分数从小到大排）
     *
     * @param key   键
     * @param start 开始排名（0为第一个）
     * @param end   结束排名（-1表示最后）
     * @return 元素集合
     */
    public Set<Object> zsRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 获取指定排名区间内的元素集合（按分数从大到小排）
     * 常用于获取排行榜前 N 名
     *
     * @param key   键
     * @param start 开始排名（0为第一名）
     * @param end   结束排名
     * @return 元素集合
     */
    public Set<Object> zsReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取指定排名区间内的元素集合，包含分数（按分数从大到小排）
     *
     * @param key   键
     * @param start 开始排名
     * @param end   结束排名
     * @return 包含值和分数的集合
     */
    public Set<ZSetOperations.TypedTuple<Object>> zsReverseRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }
}
