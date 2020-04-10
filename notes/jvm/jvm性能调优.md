## JVM性能调优



### 一、JVM调优相关参数解读

```
查看jdk1.7、jdk1.8的所有参数：java -XX:+PrintFlagsFinal -version
查看jvm启动默认参数：java -XX:+PrintCommandLineFlags -version
```



#### 1）JVM内存配置

| 参数 | 说明     | 示例     |
| ---- | -------- | -------- |
| -Xms                | 堆初始值                                             | -Xms512M             |
| -Xmx                | 堆最大值                                             | -Xmx512M             |
| -Xmn | 新生代空间大小，此处的大小是（eden+2Survivor space） | -Xmn200M |
| -Xss | 设置每个线程栈大小 | -Xss128K |
| -XX:NewSize         | 新生代空间初始值                                     | -XX:NewSize=1310M |
| -XX:MaxNewSize      | 新生代空间最大值                                     | -XX:MaxNewSize=200M  |
| -XX:MetaspaceSize | 元空间初始值 | -XX:MetaspaceSize=128M |
| -XX:MaxMetaspaceSize | 元空间最大值 | -XX:MaxMetaspaceSize=256M |
| -XX:PermSize        | 永久代空间初始值**（1.8后废弃）**                    | -XX:PermSize=128M    |
| -XX:MaxPermSize     | 永久代空间最大值**（1.8后废弃）**                    | -XX:MaxPermSize=256M |



#### 2）GC收集器配置

| 参数                    | 说明                                                         |
| ----------------------- | ------------------------------------------------------------ |
| -XX:+UseSerialGC        | 虚拟机运行在Client模式下的默认值，使用 **Serial + Serial Old** 的收集器组合进行内存回收 |
| -XX:+UseParNewGC        | 使用 **ParNew + Serial Old** 的收集器组合进行内存回收        |
| -XX:+UseConcMarkSweepGC | 使用 **ParNew + CMS + Serial Old** 的收集器组合进行内存回收。Serial Old 收集器将作为CMS收集器出现 Concurrent Mode Failure 失败后的后备收集器使用 |
| -XX:+UseParallelGC      | 虚拟机运行在Server模式下的默认值，使用 **Parallel Scavenge + Serial Old（PS MarkSweep）**的收集器组合进行内存回收 |
| -XX:+UseParallelOldGC   | 使用 **Parallel Scavenge + Parallel Old** 的收集器组合进行内存回收 |
| -XX:+UseG1GC            | G1 垃圾回收器（jdk1.7、jdk1.8都是默认关闭的）                |



#### 3）GC相关配置

| 参数                                      | 说明                                                         | 默认值 |
| ----------------------------------------- | ------------------------------------------------------------ | ------ |
| -verbose:gc                               | 输出GC日志，没有PrintGCDetails详细                           |        |
| -XX:+PrintGC                              | 输出GC日志                                                   |        |
| -XX:+PrintGCDetails                       | 输出GC的详细日志                                             |        |
| -XX:+PrintGCTimeStamps                    | 输出GC的时间戳（以基准时间的形式）                           |        |
| -XX:+PrintGCDateStamps                    | 输出GC的时间戳（以日期的形式，如2020-04-10T13:14:00.000+0800） |        |
| -XX:+PrintHeapAtGC                        | 在进行GC的前后打印出堆的信息                                 |        |
| -XX:+HeapDumpOnOutOfMemoryError           | 当JVM出现OOM时自动生成dump文件                               |        |
| -XX:HeapDumpPath=<../logs/dumpfile.hprof> | dump文件的输出路径                                           |        |
| -Xloggc:<../logs/gc.log>                  | GC日志文件的输出路径                                         |        |
| -XX:+DisableExplicitGC                    | 屏蔽掉System.gc()                                            |        |
| -XX:MinHeapFreeRatio                      | GC后java堆中空闲量占的最小比例                               | 0      |
| -XX:MaxHeapFreeRatio                      | GC后java堆中空闲量占的最大比例                               | 100    |
| -XX:NewRatio=<N>                          | 年轻代和老年代的容量比值，默认为2                            | 2      |
| -XX:SurvivorRatio=<N>                     | 新生代中Eden区域与Survivor区域的容量比值，默认为8，代表Eden : Survivor = 8 : 1 | 8      |
| -XX:PretenureSizeThreshold=<N>            | 直接晋升到老年代的对象大小，设置这个参数后，大于这个参数的对象将直接在老年代分配 |        |
| -XX:MaxTenuringThreshold=<N>              | 晋升到老年代的对象年龄。每个对象在坚持过一次Minor GC之后，年龄就增加1，当超过这个参数值时就进入老年代 |        |
| -XX:+HandlePromotionFailure               | 是否允许分配担保失败，即老年代是剩余空间不足以应付新生代的整个Eden和Survivor区的所有对象都存活的极端情况**【JDK1.6 Update 24 后废弃，规则变为只要老年代的连续空间大于新生代对象总大小或者历次晋升的平均大小就会进行Minor GC，否则进行Full GC】** |        |
| -XX:ParallelGCThreads=<N>                 | 设置并行GC时进行内存回收的线程数                             |        |
| -XX:MaxGCPauseMillis=<N>                  | 设置GC的最大停顿时间**【仅在使用Parallel Scavenge收集器时生效】** |        |
| -XX:GCTimeRatio=<N>                       | GC时间占总时间的比率，默认值为99，即允许1%的GC时间**【仅在使用Parallel Scavenge收集器时生效】** | 99     |
| -XX:+UseAdaptiveSizePolicy                | 开启GC自适应调节策略**【仅在使用Parallel Scavenge收集器时生效】** |        |
| -XX:CMSInitiatingOccupancyFraction=<N>    | 设置CMS收集器在老年代空间被使用多少后触发垃圾收集**【仅在使用CMS收集器时生效】** | -1     |
| -XX:+UseCMSCompactAtFullCollection        | 设置CMS收集器在完成垃圾收集后是否要进行一次内存碎片整理**【仅在使用CMS收集器时生效】** |        |
| -XX:CMSFullGCsBeforeCompaction=<N>        | 设置CMS收集器在进行若干次垃圾收集后再启动一次内存碎片整理**【仅在使用CMS收集器时生效】** | 0      |




### 二、实例
* Java堆溢出

```java
package com.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description 堆溢出测试
 * @VM args: -Xms20M -Xmx20M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=F:/JVM/dumpfile.hprof -XX:+PrintGCDetails -Xloggc:F:/JVM/heap_trace.txt
 * @date 2018/9/10 22:13
 */
public class HeapOutOfMemory {

    public static void main(String[] args){
        List<TestCase> cases = new ArrayList<>();
        while (true) {
            cases.add(new TestCase());
        }
    }
}

package com.jvm;

/**
 * @author luoc
 * @version V1.0.0
 * @description 测试用例
 * @date 2018/9/10 22:20
 */
public class TestCase {
}

```

* Java栈溢出

```java
package com.jvm;

/**
 * @author luoc
 * @version V1.0.0
 * @description Java栈溢出
 * @VM args: -Xss128K
 * @date 2018/9/10 22:52
 */
public class StackOverFlow {

    private int i;

    public void plus() {
        i++;
        plus();
    }

    public static void main(String[] args){
        StackOverFlow stackOverFlow = new StackOverFlow();
        try {
            stackOverFlow.plus();
        } catch (Exception e) {
            System.out.println("Exception:stack length: " + stackOverFlow.i);
            e.printStackTrace();
        } catch (Error e) {
            System.out.println("Error:stack length: " + stackOverFlow.i);
            e.printStackTrace();
        }
    }
}

```
