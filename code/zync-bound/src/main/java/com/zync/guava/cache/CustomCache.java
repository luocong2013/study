package com.zync.guava.cache;

import com.google.common.cache.*;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @description 自定义缓存
 * @date 2020/5/21 9:57
 */
public class CustomCache {

    public static void main(String[] args) throws Exception {
        CustomCache cache = new CustomCache();
         //cache.cache();
        cache.loadingCache();

    }
    public void cache() throws InterruptedException, ExecutionException {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                // 设置缓存的初始容量（大小）
                .initialCapacity(100)
                // 设置缓存的最大容量（大小）
                .maximumSize(100)

                // 当缓存的最大数量/容量逼近最大值时，Guava会使用LRU算法对缓存进行回收
                // 设置最大容量 为 1M（容量）
                // .maximumWeight(1 << 30)
                // 设置用来计算缓存容量的Weigher
                // .weigher((Weigher<String, String>) (key, value) -> key.getBytes().length + value.getBytes().length)

                // 设置缓存在写入一分钟后失效
                // .expireAfterWrite(3, TimeUnit.SECONDS)
                // 设置缓存在被访问后一分钟失效
                 .expireAfterAccess(3, TimeUnit.SECONDS)
                // 设置并发级别
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .removalListener(new RemovalListener<String, String>() {
                    // 移除监听器
                    @Override
                    public void onRemoval(RemovalNotification<String, String> notification) {
                        System.out.println(notification.getKey() + "——被移除了");
                    }
                })
                // 开启缓存统计
                .recordStats()
                .build();

        // 回收所有缓存
        // cache.invalidateAll();

        cache.put("k1", "v1");

        TimeUnit.SECONDS.sleep(2);

        // 获取一个缓存，如果该缓存不存在则返回一个null值
        Object value = cache.getIfPresent("k1");
        System.out.println(value);

        TimeUnit.SECONDS.sleep(2);

        // 获取缓存，当缓存不存在时，则通Callable进行加载并返回。该操作是原子
        String value1 = cache.get("k1", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "call";
            }
        });
        System.out.println(value1);
    }

    public void loadingCache() throws InterruptedException, ExecutionException {
        // 缓存刷新
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                // 设置缓存在写入10分钟后，通过CacheLoader的load方法进行刷新
                //.refreshAfterWrite(Duration.ofSeconds(3))
                .expireAfterAccess(Duration.ofSeconds(3))
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        // 缓存加载逻辑
                        System.out.println("load method loadingCache_" + key);
                        return "loadingCache_" + key;
                    }
                });
        // loadingCache 在进行刷新时无需显式的传入 value
        //cache.refresh("k1");

        cache.put("k1", "v1");
        cache.put("k2", "v2");
        cache.put("k3", "v3");

        TimeUnit.SECONDS.sleep(3);

        System.out.println(cache.getIfPresent("k1"));
        System.out.println(cache.get("k1"));
        System.out.println(cache.get("k1"));

    }
}
