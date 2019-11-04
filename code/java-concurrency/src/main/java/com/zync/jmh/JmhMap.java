package com.zync.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 几种map的jmh测试
 * @date 2019/11/4 21:58
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class JmhMap {

    private static Map<String, String> hashMap = new HashMap<>(10240);
    private static Map<String, String> syncHashMap = Collections.synchronizedMap(new HashMap<>(10240));
    private static Map<String, String> concurrentHashMap = new ConcurrentHashMap<>(10240);

    @Setup
    public void setup() {
        for (int i = 0; i < 10000; i++) {
            hashMap.put(Integer.toString(i), Integer.toString(i));
            syncHashMap.put(Integer.toString(i), Integer.toString(i));
            concurrentHashMap.put(Integer.toString(i), Integer.toString(i));
        }
    }

    @Benchmark
    public void hashMapGet() {
        hashMap.get("40");
    }

    @Benchmark
    public void syncHashMapGet() {
        syncHashMap.get("40");
    }

    @Benchmark
    public void concurrentHashMapGet() {
        concurrentHashMap.get("40");
    }

    @Benchmark
    public void hashMapSize() {
        hashMap.size();
    }

    @Benchmark
    public void syncHashMapSize() {
        syncHashMap.size();
    }

    @Benchmark
    public void concurrentHashMapSize() {
        concurrentHashMap.size();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JmhMap.class.getSimpleName())
                // 线程数量
                .forks(1)
                // 预热5次
                .warmupIterations(5)
                // 正式运行测试5次
                .measurementIterations(5)
                .build();
        new Runner(opt).run();
    }
}
