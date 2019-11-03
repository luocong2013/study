1. 模式（Mode）
    Throughput: 整体吞吐量，表示1秒内可以执行多少次调用
    AverageTime: 调用的平均时间，指每一次调用所需的时间
    SampleTime: 随机取样，最后输出取样结果的分布
    SingleShotTime: 以上模式都是默认一次Iteration是1秒，唯有SingleShotTime只能运行一次。往往同时把warmup次数设为0，用于测试冷启动时的性能
2. 迭代（Iteration）
    迭代是JMH的一次测量单位。在大部分测量模式下，一次迭代表示1秒。在这一秒内会不断调用被测方法。并采样计算吞吐量、平均时间等
3. 预热（Warmup）
    由于Java虚拟机的JIT的存在，同一个方法在JIT编译前后的时间将会不同。通常只考虑方法在JIT编译后的性能
4. 状态（State）
    通过State可以指定一个对象的作用范围，范围主要有两种。一种为线程范围，也就是一个对象只会被一个线程访问。在多线程池测试时，会为每一个线程生
    成一个对象。另一种是基准测试范围（Benchmark），即多个线程共享一个实例。
5. 配置类（Options/OptionsBuilder）
    在测试开始前，首先要对测试进行配置。通常需要指定一些参数，比如：指定测试类（include）、使用的进程个数（fork）、预热迭代次数（Warmup Iterations）