package com.zync.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption JMH简单示例
 * @date 2019/10/28 22:32
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class JmhSample {

    /**
     * 测试平均执行时间
     * @throws InterruptedException
     */
    @Benchmark
    public void wellHelloThere() throws InterruptedException {
        // 这个就是被度量的代码函数，用 @Benchmark 标注
        TimeUnit.MILLISECONDS.sleep(10);
    }

    /**
     * 吞吐量测试
     * @throws InterruptedException
     */
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureThroughput() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    /**
     * 测试采样
     * @throws InterruptedException
     */
    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureSamples() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JmhSample.class.getSimpleName())
                // 线程数量
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
