# OpenJDK 11 JVM日志相关参数



## 一、生产环境JDK 17 JVM参数配置

```
-Xms5440M
-Xmx5440M
-XX:MetaspaceSize=512M
-XX:MaxMetaspaceSize=512M
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=logs/
-XX:+UseZGC
-XX:-OmitStackTraceInFastThrow
-Xlog:gc*=info,gc+stats=warning:file=logs/xycrm_agent_gc_%t.log:time,level,tags,hostname,pid,tid:filecount=5,filesize=100M
```



- -XX:-OmitStackTraceInFastThrow  禁止快速抛异常，当同一个位置的反复出现异常的时候，jvm会在一定次数后只抛出一个简易异常，没有堆栈的那种，定位不便，所以给禁了。但是禁止之后对性能有影响
- -XX:HeapDumpPath  `OOM`之后自动 dump 内存到指定目录



## 二、GC日志解析

```logs
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Initializing The Z Garbage Collector
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Version: 17.0.8.1+1 (release)
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] NUMA Support: Disabled
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] CPUs: 8 total, 2 available
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Memory: 4096M
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Large Page Support: Disabled
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] GC Workers: 1 (dynamic)
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Address Space Type: Contiguous/Unrestricted/Complete
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Address Space Size: 49152M x 3 = 147456M
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Heap Backing File: /memfd:java_heap
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Heap Backing Filesystem: tmpfs (0x1021994)
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Min Capacity: 8M
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Initial Capacity: 3072M
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Max Capacity: 3072M
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Medium Page Size: 32M
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Pre-touch: Disabled
[2024-05-23T17:04:55.128+0800][1][9][info][gc,init] Available space on backing filesystem: N/A
[2024-05-23T17:04:55.129+0800][1][9][info][gc,init] Uncommit: Enabled
[2024-05-23T17:04:55.129+0800][1][9][info][gc,init] Uncommit Delay: 300s
[2024-05-23T17:04:55.721+0800][1][9][info][gc,init] Runtime Workers: 2
[2024-05-23T17:04:55.722+0800][1][9][info][gc     ] Using The Z Garbage Collector
[2024-05-23T17:04:55.817+0800][1][9][info][gc,metaspace] CDS archive(s) mapped at: [0x00007fccb8000000-0x00007fccb8bc1000-0x00007fccb8bc1000), size 12324864, SharedBaseAddress: 0x00007fccb8000000, ArchiveRelocationMode: 1.
[2024-05-23T17:04:55.817+0800][1][9][info][gc,metaspace] Compressed class space mapped at: 0x00007fccb9000000-0x00007fccc0000000, reserved size: 117440512
[2024-05-23T17:04:55.817+0800][1][9][info][gc,metaspace] Narrow klass base: 0x00007fccb8000000, Narrow klass shift: 0, Narrow klass range: 0x100000000
[2024-05-23T17:04:59.890+0800][1][13][info][gc,start    ] GC(0) Garbage Collection (Warmup)
[2024-05-23T17:04:59.890+0800][1][13][info][gc,task     ] GC(0) Using 1 workers
[2024-05-23T17:04:59.890+0800][1][18][info][gc,phases   ] GC(0) Pause Mark Start 0.021ms
[2024-05-23T17:05:00.022+0800][1][13][info][gc,phases   ] GC(0) Concurrent Mark 131.278ms
[2024-05-23T17:05:00.022+0800][1][18][info][gc,phases   ] GC(0) Pause Mark End 0.021ms
[2024-05-23T17:05:00.022+0800][1][13][info][gc,phases   ] GC(0) Concurrent Mark Free 0.001ms
[2024-05-23T17:05:00.028+0800][1][13][info][gc,phases   ] GC(0) Concurrent Process Non-Strong References 5.873ms
[2024-05-23T17:05:00.028+0800][1][13][info][gc,phases   ] GC(0) Concurrent Reset Relocation Set 0.000ms
[2024-05-23T17:05:00.034+0800][1][13][info][gc,phases   ] GC(0) Concurrent Select Relocation Set 5.883ms
[2024-05-23T17:05:00.034+0800][1][18][info][gc,phases   ] GC(0) Pause Relocate Start 0.017ms
[2024-05-23T17:05:00.071+0800][1][13][info][gc,phases   ] GC(0) Concurrent Relocate 36.700ms
[2024-05-23T17:05:00.071+0800][1][13][info][gc,load     ] GC(0) Load: 1.01/0.66/0.54
[2024-05-23T17:05:00.071+0800][1][13][info][gc,mmu      ] GC(0) MMU: 2ms/98.9%, 5ms/99.6%, 10ms/99.8%, 20ms/99.8%, 50ms/99.9%, 100ms/100.0%
[2024-05-23T17:05:00.071+0800][1][13][info][gc,marking  ] GC(0) Mark: 1 stripe(s), 2 proactive flush(es), 1 terminate flush(es), 0 completion(s), 0 continuation(s) 
[2024-05-23T17:05:00.071+0800][1][13][info][gc,marking  ] GC(0) Mark Stack Usage: 32M
[2024-05-23T17:05:00.071+0800][1][13][info][gc,nmethod  ] GC(0) NMethods: 2506 registered, 0 unregistered
[2024-05-23T17:05:00.071+0800][1][13][info][gc,metaspace] GC(0) Metaspace: 11M used, 11M committed, 176M reserved
[2024-05-23T17:05:00.071+0800][1][13][info][gc,ref      ] GC(0) Soft: 3574 encountered, 0 discovered, 0 enqueued
[2024-05-23T17:05:00.071+0800][1][13][info][gc,ref      ] GC(0) Weak: 1550 encountered, 609 discovered, 239 enqueued
[2024-05-23T17:05:00.071+0800][1][13][info][gc,ref      ] GC(0) Final: 1 encountered, 0 discovered, 0 enqueued
[2024-05-23T17:05:00.071+0800][1][13][info][gc,ref      ] GC(0) Phantom: 983 encountered, 893 discovered, 652 enqueued
[2024-05-23T17:05:00.071+0800][1][13][info][gc,reloc    ] GC(0) Small Pages: 161 / 322M, Empty: 0M, Relocated: 10M, In-Place: 0
[2024-05-23T17:05:00.071+0800][1][13][info][gc,reloc    ] GC(0) Medium Pages: 0 / 0M, Empty: 0M, Relocated: 0M, In-Place: 0
[2024-05-23T17:05:00.071+0800][1][13][info][gc,reloc    ] GC(0) Large Pages: 0 / 0M, Empty: 0M, Relocated: 0M, In-Place: 0
[2024-05-23T17:05:00.071+0800][1][13][info][gc,reloc    ] GC(0) Forwarding Usage: 4M
[2024-05-23T17:05:00.071+0800][1][13][info][gc,heap     ] GC(0) Min Capacity: 8M(0%)
[2024-05-23T17:05:00.071+0800][1][13][info][gc,heap     ] GC(0) Max Capacity: 3072M(100%)
[2024-05-23T17:05:00.071+0800][1][13][info][gc,heap     ] GC(0) Soft Max Capacity: 3072M(100%)
[2024-05-23T17:05:00.071+0800][1][13][info][gc,heap     ] GC(0)                Mark Start          Mark End        Relocate Start      Relocate End           High               Low         
[2024-05-23T17:05:00.071+0800][1][13][info][gc,heap     ] GC(0)  Capacity:     3072M (100%)       3072M (100%)       3072M (100%)       3072M (100%)       3072M (100%)       3072M (100%)   
[2024-05-23T17:05:00.071+0800][1][13][info][gc,heap     ] GC(0)      Free:     2750M (90%)        2728M (89%)        2724M (89%)        3022M (98%)        3022M (98%)        2720M (89%)    
[2024-05-23T17:05:00.071+0800][1][13][info][gc,heap     ] GC(0)      Used:      322M (10%)         344M (11%)         348M (11%)          50M (2%)          352M (11%)          50M (2%)     
[2024-05-23T17:05:00.071+0800][1][13][info][gc,heap     ] GC(0)      Live:         -                14M (0%)           14M (0%)           14M (0%)             -                  -          
[2024-05-23T17:05:00.071+0800][1][13][info][gc,heap     ] GC(0) Allocated:         -                22M (1%)           26M (1%)           31M (1%)             -                  -          
[2024-05-23T17:05:00.071+0800][1][13][info][gc,heap     ] GC(0)   Garbage:         -               307M (10%)         307M (10%)           3M (0%)             -                  -          
[2024-05-23T17:05:00.071+0800][1][13][info][gc,heap     ] GC(0) Reclaimed:         -                  -                 0M (0%)          303M (10%)            -                  -          
[2024-05-23T17:05:00.071+0800][1][13][info][gc          ] GC(0) Garbage Collection (Warmup) 322M(10%)->50M(2%)

...

# 第三次触发 ZGC，触发原因是 Proactive
[2024-05-23T21:36:08.890+0800][1][13][info][gc,start    ] GC(3) Garbage Collection (Proactive)

# 使用了一个并行工作线程
[2024-05-23T21:36:08.890+0800][1][13][info][gc,task     ] GC(3) Using 1 workers

# 第1步，初始标记 STW 时间，0.035ms
[2024-05-23T21:36:08.890+0800][1][18][info][gc,phases   ] GC(3) Pause Mark Start 0.035ms

# 第2步，并发标记时间，102.560ms，没有 STW
[2024-05-23T21:36:08.993+0800][1][13][info][gc,phases   ] GC(3) Concurrent Mark 102.560ms

# 第3步，再标记 STW 时间，0.022ms
[2024-05-23T21:36:08.993+0800][1][18][info][gc,phases   ] GC(3) Pause Mark End 0.022ms

# 第4步，不知道是啥
[2024-05-23T21:36:08.993+0800][1][13][info][gc,phases   ] GC(3) Concurrent Mark Free 0.001ms

# 第5步，引用处理
[2024-05-23T21:36:09.019+0800][1][13][info][gc,phases   ] GC(3) Concurrent Process Non-Strong References 25.765ms

# 第6步，重置转移集
[2024-05-23T21:36:09.019+0800][1][13][info][gc,phases   ] GC(3) Concurrent Reset Relocation Set 0.015ms

# 第7步，选择转移集
[2024-05-23T21:36:09.021+0800][1][13][info][gc,phases   ] GC(3) Concurrent Select Relocation Set 1.797ms

# 第8步，初始转移 STW 时间，0.011ms
[2024-05-23T21:36:09.021+0800][1][18][info][gc,phases   ] GC(3) Pause Relocate Start 0.011ms

# 第9步，并发转移
[2024-05-23T21:36:09.026+0800][1][13][info][gc,phases   ] GC(3) Concurrent Relocate 4.996ms

# load信息，分别是 1min/5min/15min
[2024-05-23T21:36:09.026+0800][1][13][info][gc,load     ] GC(3) Load: 0.08/0.19/0.29

# MMU，应用程序运行所占的比例，改值越大，说明应用程序效率越高（其实就是大家说的 吞吐量）
[2024-05-23T21:36:09.026+0800][1][13][info][gc,mmu      ] GC(3) MMU: 2ms/98.2%, 5ms/99.3%, 10ms/99.6%, 20ms/99.8%, 50ms/99.9%, 100ms/100.0%

# 一个标记条带，两次主动刷新（0号工作线程暂停了两个应用程序去刷新标记栈），一次被动刷新
[2024-05-23T21:36:09.026+0800][1][13][info][gc,marking  ] GC(3) Mark: 1 stripe(s), 2 proactive flush(es), 1 terminate flush(es), 0 completion(s), 0 continuation(s) 

# 标记栈 使用了 32M
[2024-05-23T21:36:09.026+0800][1][13][info][gc,marking  ] GC(3) Mark Stack Usage: 32M

# jvm 优化了 7353 个方法，937 个没有优化
[2024-05-23T21:36:09.026+0800][1][13][info][gc,nmethod  ] GC(3) NMethods: 7353 registered, 937 unregistered

# 元空间 使用了 53M，提交了 54M，还剩 176M
[2024-05-23T21:36:09.026+0800][1][13][info][gc,metaspace] GC(3) Metaspace: 53M used, 54M committed, 176M reserved

# 引用处理情况 start
[2024-05-23T21:36:09.026+0800][1][13][info][gc,ref      ] GC(3) Soft: 5798 encountered, 3655 discovered, 3452 enqueued
[2024-05-23T21:36:09.026+0800][1][13][info][gc,ref      ] GC(3) Weak: 5323 encountered, 651 discovered, 20 enqueued
[2024-05-23T21:36:09.026+0800][1][13][info][gc,ref      ] GC(3) Final: 2 encountered, 0 discovered, 0 enqueued
[2024-05-23T21:36:09.026+0800][1][13][info][gc,ref      ] GC(3) Phantom: 208 encountered, 109 discovered, 20 enqueued
# 引用处理情况 end

# 页 start
[2024-05-23T21:36:09.026+0800][1][13][info][gc,reloc    ] GC(3) Small Pages: 34 / 68M, Empty: 8M, Relocated: 4M, In-Place: 0
[2024-05-23T21:36:09.026+0800][1][13][info][gc,reloc    ] GC(3) Medium Pages: 0 / 0M, Empty: 0M, Relocated: 0M, In-Place: 0
[2024-05-23T21:36:09.026+0800][1][13][info][gc,reloc    ] GC(3) Large Pages: 0 / 0M, Empty: 0M, Relocated: 0M, In-Place: 0
[2024-05-23T21:36:09.026+0800][1][13][info][gc,reloc    ] GC(3) Forwarding Usage: 1M
# 页 end

# 堆的容量信息 start
[2024-05-23T21:36:09.026+0800][1][13][info][gc,heap     ] GC(3) Min Capacity: 8M(0%)
[2024-05-23T21:36:09.026+0800][1][13][info][gc,heap     ] GC(3) Max Capacity: 3072M(100%)
[2024-05-23T21:36:09.026+0800][1][13][info][gc,heap     ] GC(3) Soft Max Capacity: 3072M(100%)
# 堆的容量信息 end

# 统计信息，Free + Used = 堆空间 = -Xmx，java17 删除了 java11 里的 reserve 概念
[2024-05-23T21:36:09.026+0800][1][13][info][gc,heap     ] GC(3)                Mark Start          Mark End        Relocate Start      Relocate End           High               Low         
[2024-05-23T21:36:09.026+0800][1][13][info][gc,heap     ] GC(3)  Capacity:      924M (30%)         924M (30%)         924M (30%)         924M (30%)         924M (30%)         924M (30%)    
[2024-05-23T21:36:09.026+0800][1][13][info][gc,heap     ] GC(3)      Free:     3004M (98%)        3002M (98%)        3008M (98%)        3032M (99%)        3032M (99%)        3000M (98%)    
[2024-05-23T21:36:09.026+0800][1][13][info][gc,heap     ] GC(3)      Used:       68M (2%)           70M (2%)           64M (2%)           40M (1%)           72M (2%)           40M (1%)     
[2024-05-23T21:36:09.026+0800][1][13][info][gc,heap     ] GC(3)      Live:         -                28M (1%)           28M (1%)           28M (1%)             -                  -          
[2024-05-23T21:36:09.026+0800][1][13][info][gc,heap     ] GC(3) Allocated:         -                 2M (0%)            4M (0%)            4M (0%)             -                  -          
[2024-05-23T21:36:09.026+0800][1][13][info][gc,heap     ] GC(3)   Garbage:         -                39M (1%)           31M (1%)            7M (0%)             -                  -          
[2024-05-23T21:36:09.026+0800][1][13][info][gc,heap     ] GC(3) Reclaimed:         -                  -                 8M (0%)           32M (1%)             -                  -          

# 此次GC，内存前后对比情况，GC前 堆内存使用率是 2%，GC后是 1%
[2024-05-23T21:36:09.026+0800][1][13][info][gc          ] GC(3) Garbage Collection (Proactive) 68M(2%)->40M(1%)

...

[2024-07-04T07:25:44.296+0800][gc,stats    ] === Garbage Collection Statistics =======================================================================================================================
[2024-07-04T07:25:44.296+0800][gc,stats    ]                                                              Last 10s              Last 10m              Last 10h                Total
[2024-07-04T07:25:44.296+0800][gc,stats    ]                                                              Avg / Max             Avg / Max             Avg / Max             Avg / Max
[2024-07-04T07:25:44.296+0800][gc,stats    ]   Collector: Garbage Collection Cycle                      0.000 / 0.000       119.627 / 122.219     118.308 / 133.205     118.466 / 462.670     ms
[2024-07-04T07:25:44.296+0800][gc,stats    ]  Contention: Mark Segment Reset Contention                     0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]  Contention: Mark SeqNum Reset Contention                      0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]    Critical: Allocation Stall                                  0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]    Critical: Allocation Stall                              0.000 / 0.000         0.000 / 0.000         0.000 / 0.000         0.000 / 0.000       ms
[2024-07-04T07:25:44.296+0800][gc,stats    ]    Critical: GC Locker Stall                                   0 / 0                 0 / 0                 0 / 0                 0 / 1           ops/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]    Critical: GC Locker Stall                               0.000 / 0.000         0.000 / 0.000         0.000 / 0.000         0.001 / 0.001       ms
[2024-07-04T07:25:44.296+0800][gc,stats    ]    Critical: Relocation Stall                                  0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]    Critical: Relocation Stall                              0.000 / 0.000         0.000 / 0.000         0.000 / 0.000         0.000 / 0.000       ms
[2024-07-04T07:25:44.296+0800][gc,stats    ]      Memory: Allocation Rate                                   0 / 0                 0 / 10                0 / 14                0 / 260         MB/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]      Memory: Out Of Memory                                     0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]      Memory: Page Cache Flush                                  0 / 0                 0 / 0                 0 / 0                 0 / 0           MB/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]      Memory: Page Cache Hit L1                                 0 / 0                 0 / 6                 0 / 7                 0 / 112         ops/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]      Memory: Page Cache Hit L2                                 0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]      Memory: Page Cache Hit L3                                 0 / 0                 0 / 0                 0 / 0                 0 / 116         ops/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]      Memory: Page Cache Miss                                   0 / 0                 0 / 0                 0 / 4                 0 / 72          ops/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]      Memory: Uncommit                                          0 / 0                 0 / 4                 0 / 8                 0 / 2040        MB/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]      Memory: Undo Object Allocation Failed                     0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]      Memory: Undo Object Allocation Succeeded                  0 / 0                 0 / 0                 0 / 1                 0 / 3           ops/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]      Memory: Undo Page Allocation                              0 / 0                 0 / 0                 0 / 0                 0 / 1           ops/s
[2024-07-04T07:25:44.296+0800][gc,stats    ]       Phase: Concurrent Mark                               0.000 / 0.000        79.279 / 80.244       78.556 / 85.422       79.385 / 326.089     ms
[2024-07-04T07:25:44.296+0800][gc,stats    ]       Phase: Concurrent Mark Continue                      0.000 / 0.000         0.000 / 0.000         0.000 / 0.000         0.000 / 0.000       ms
[2024-07-04T07:25:44.296+0800][gc,stats    ]       Phase: Concurrent Mark Free                          0.000 / 0.000         0.001 / 0.001         0.001 / 0.002         0.001 / 0.050       ms
[2024-07-04T07:25:44.296+0800][gc,stats    ]       Phase: Concurrent Process Non-Strong References      0.000 / 0.000        36.881 / 38.230       36.252 / 52.643       35.304 / 61.619      ms
[2024-07-04T07:25:44.296+0800][gc,stats    ]       Phase: Concurrent Relocate                           0.000 / 0.000         1.222 / 1.453         1.250 / 1.845         1.504 / 44.168      ms
[2024-07-04T07:25:44.296+0800][gc,stats    ]       Phase: Concurrent Reset Relocation Set               0.000 / 0.000         0.005 / 0.005         0.005 / 0.039         0.005 / 0.109       ms
[2024-07-04T07:25:44.296+0800][gc,stats    ]       Phase: Concurrent Select Relocation Set              0.000 / 0.000         1.480 / 1.498         1.510 / 2.128         1.523 / 56.610      ms
[2024-07-04T07:25:44.296+0800][gc,stats    ]       Phase: Pause Mark End                                0.000 / 0.000         0.022 / 0.029         0.017 / 0.033         0.017 / 0.124       ms
[2024-07-04T07:25:44.296+0800][gc,stats    ]       Phase: Pause Mark Start                              0.000 / 0.000         0.026 / 0.029         0.020 / 0.042         0.020 / 1.589       ms
[2024-07-04T07:25:44.296+0800][gc,stats    ]       Phase: Pause Relocate Start                          0.000 / 0.000         0.012 / 0.013         0.011 / 0.033         0.011 / 0.102       ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]    Subphase: Concurrent Classes Purge                      0.000 / 0.000         1.180 / 1.605         0.819 / 1.605         0.821 / 4.651       ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]    Subphase: Concurrent Classes Unlink                     0.000 / 0.000        33.114 / 34.034       32.842 / 49.305       31.876 / 57.747      ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]    Subphase: Concurrent Mark                               0.000 / 0.000        73.815 / 74.668       72.987 / 78.044       73.743 / 317.293     ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]    Subphase: Concurrent Mark Try Flush                     0.000 / 0.000         0.133 / 0.304         0.142 / 1.045         0.141 / 14.755      ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]    Subphase: Concurrent Mark Try Terminate                 0.000 / 0.000         0.025 / 0.053         0.027 / 0.104         0.027 / 3.584       ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]    Subphase: Concurrent References Enqueue                 0.000 / 0.000         0.009 / 0.010         0.009 / 0.021         0.009 / 0.160       ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]    Subphase: Concurrent References Process                 0.000 / 0.000         0.277 / 0.306         0.272 / 0.480         0.274 / 21.824      ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]    Subphase: Concurrent Roots ClassLoaderDataGraph         0.000 / 0.000         0.434 / 0.437         0.453 / 0.628         0.461 / 1.758       ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]    Subphase: Concurrent Roots CodeCache                    0.000 / 0.000         0.000 / 0.000         0.000 / 0.000         0.000 / 0.000       ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]    Subphase: Concurrent Roots JavaThreads                  0.000 / 0.000         4.731 / 4.824         4.814 / 7.219         4.879 / 9.084       ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]    Subphase: Concurrent Roots OopStorageSet                0.000 / 0.000         0.110 / 0.120         0.107 / 0.165         0.111 / 1.052       ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]    Subphase: Concurrent Weak Roots OopStorageSet           0.000 / 0.000         1.986 / 2.034         2.004 / 2.870         2.014 / 4.046       ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]    Subphase: Pause Mark Try Complete                       0.000 / 0.000         0.000 / 0.000         0.000 / 0.000         0.010 / 0.010       ms
[2024-07-04T07:25:44.297+0800][gc,stats    ]      System: Java Threads                                      0 / 0                98 / 98               97 / 100              97 / 100         threads
[2024-07-04T07:25:44.297+0800][gc,stats    ] =========================================================================================================================================================
[2024-07-04T07:25:54.297+0800][gc,stats    ] === Garbage Collection Statistics =======================================================================================================================
[2024-07-04T07:25:54.297+0800][gc,stats    ]                                                              Last 10s              Last 10m              Last 10h                Total
[2024-07-04T07:25:54.297+0800][gc,stats    ]                                                              Avg / Max             Avg / Max             Avg / Max             Avg / Max
[2024-07-04T07:25:54.297+0800][gc,stats    ]   Collector: Garbage Collection Cycle                      0.000 / 0.000       119.627 / 122.219     118.308 / 133.205     118.466 / 462.670     ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]  Contention: Mark Segment Reset Contention                     0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]  Contention: Mark SeqNum Reset Contention                      0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Critical: Allocation Stall                                  0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Critical: Allocation Stall                              0.000 / 0.000         0.000 / 0.000         0.000 / 0.000         0.000 / 0.000       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Critical: GC Locker Stall                                   0 / 0                 0 / 0                 0 / 0                 0 / 1           ops/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Critical: GC Locker Stall                               0.000 / 0.000         0.000 / 0.000         0.000 / 0.000         0.001 / 0.001       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Critical: Relocation Stall                                  0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Critical: Relocation Stall                              0.000 / 0.000         0.000 / 0.000         0.000 / 0.000         0.000 / 0.000       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]      Memory: Allocation Rate                                   0 / 0                 0 / 10                0 / 14                0 / 260         MB/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]      Memory: Out Of Memory                                     0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]      Memory: Page Cache Flush                                  0 / 0                 0 / 0                 0 / 0                 0 / 0           MB/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]      Memory: Page Cache Hit L1                                 0 / 0                 0 / 6                 0 / 7                 0 / 112         ops/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]      Memory: Page Cache Hit L2                                 0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]      Memory: Page Cache Hit L3                                 0 / 0                 0 / 0                 0 / 0                 0 / 116         ops/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]      Memory: Page Cache Miss                                   0 / 0                 0 / 0                 0 / 4                 0 / 72          ops/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]      Memory: Uncommit                                          0 / 0                 0 / 4                 0 / 8                 0 / 2040        MB/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]      Memory: Undo Object Allocation Failed                     0 / 0                 0 / 0                 0 / 0                 0 / 0           ops/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]      Memory: Undo Object Allocation Succeeded                  0 / 0                 0 / 0                 0 / 1                 0 / 3           ops/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]      Memory: Undo Page Allocation                              0 / 0                 0 / 0                 0 / 0                 0 / 1           ops/s
[2024-07-04T07:25:54.297+0800][gc,stats    ]       Phase: Concurrent Mark                               0.000 / 0.000        79.279 / 80.244       78.556 / 85.422       79.385 / 326.089     ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]       Phase: Concurrent Mark Continue                      0.000 / 0.000         0.000 / 0.000         0.000 / 0.000         0.000 / 0.000       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]       Phase: Concurrent Mark Free                          0.000 / 0.000         0.001 / 0.001         0.001 / 0.002         0.001 / 0.050       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]       Phase: Concurrent Process Non-Strong References      0.000 / 0.000        36.881 / 38.230       36.252 / 52.643       35.304 / 61.619      ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]       Phase: Concurrent Relocate                           0.000 / 0.000         1.222 / 1.453         1.250 / 1.845         1.504 / 44.168      ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]       Phase: Concurrent Reset Relocation Set               0.000 / 0.000         0.005 / 0.005         0.005 / 0.039         0.005 / 0.109       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]       Phase: Concurrent Select Relocation Set              0.000 / 0.000         1.480 / 1.498         1.510 / 2.128         1.523 / 56.610      ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]       Phase: Pause Mark End                                0.000 / 0.000         0.022 / 0.029         0.017 / 0.033         0.017 / 0.124       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]       Phase: Pause Mark Start                              0.000 / 0.000         0.026 / 0.029         0.020 / 0.042         0.020 / 1.589       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]       Phase: Pause Relocate Start                          0.000 / 0.000         0.012 / 0.013         0.011 / 0.033         0.011 / 0.102       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Subphase: Concurrent Classes Purge                      0.000 / 0.000         1.180 / 1.605         0.819 / 1.605         0.821 / 4.651       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Subphase: Concurrent Classes Unlink                     0.000 / 0.000        33.114 / 34.034       32.842 / 49.305       31.876 / 57.747      ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Subphase: Concurrent Mark                               0.000 / 0.000        73.815 / 74.668       72.987 / 78.044       73.743 / 317.293     ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Subphase: Concurrent Mark Try Flush                     0.000 / 0.000         0.133 / 0.304         0.142 / 1.045         0.141 / 14.755      ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Subphase: Concurrent Mark Try Terminate                 0.000 / 0.000         0.025 / 0.053         0.027 / 0.104         0.027 / 3.584       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Subphase: Concurrent References Enqueue                 0.000 / 0.000         0.009 / 0.010         0.009 / 0.021         0.009 / 0.160       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Subphase: Concurrent References Process                 0.000 / 0.000         0.277 / 0.306         0.272 / 0.480         0.274 / 21.824      ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Subphase: Concurrent Roots ClassLoaderDataGraph         0.000 / 0.000         0.434 / 0.437         0.453 / 0.628         0.461 / 1.758       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Subphase: Concurrent Roots CodeCache                    0.000 / 0.000         0.000 / 0.000         0.000 / 0.000         0.000 / 0.000       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Subphase: Concurrent Roots JavaThreads                  0.000 / 0.000         4.731 / 4.824         4.814 / 7.219         4.879 / 9.084       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Subphase: Concurrent Roots OopStorageSet                0.000 / 0.000         0.110 / 0.120         0.107 / 0.165         0.111 / 1.052       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Subphase: Concurrent Weak Roots OopStorageSet           0.000 / 0.000         1.986 / 2.034         2.004 / 2.870         2.014 / 4.046       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]    Subphase: Pause Mark Try Complete                       0.000 / 0.000         0.000 / 0.000         0.000 / 0.000         0.010 / 0.010       ms
[2024-07-04T07:25:54.297+0800][gc,stats    ]      System: Java Threads                                      0 / 0                98 / 98               97 / 100              97 / 100         threads
[2024-07-04T07:25:54.297+0800][gc,stats    ] =========================================================================================================================================================
```



### [gc,init]

是 gc 初始化的日志，打印相关的信息



### [gc,start    ] Garbage Collection (Warmup) （预热）

是触发 `ZGC` 开始回收内存的标识

其中 [gc,start    ] GC(0) Garbage Collection (Warmup) 是服务刚启动时出现，一般不需要关注



### [gc,start    ] Garbage Collection (Proactive)

`[2024-05-23T21:36:08.890+0800][1][13][info][gc,start    ] GC(3) Garbage Collection (Proactive)`

完整信息的格式是jvm参数配置的，为：time,level,tags,hostname,pid,tid

其中 GC(3) 这个3表示是第三次GC

Proactive 表示是GC的触发原因：ZGC默认的主动触发规则

注: 具体GC日志解读可以查看上面的完整信息标准



### [gc,stats    ] Garbage Collection Statistics 

gc日志会大概间隔 `10s` 输出一次 统计信息，分别是最近 10秒、10分钟、10小时的统计信息
所以在应用负载很低的，没有触发 `GC` 的时候，这个统计信息会反复、重复出现





### GC日志中每一行都注明了GC过程中的信息，关键信息如下：

- **Start**：开始GC，并标明的GC触发的原因。上图中触发原因是自适应算法。
- **Phase-Pause Mark Start**：初始标记，会STW。
- **Phase-Pause Mark End**：再次标记，会STW。
- **Phase-Pause Relocate Start**：初始转移，会STW。
- **Heap信息**：记录了GC过程中Mark、Relocate前后的堆大小变化状况。High和Low记录了其中的最大值和最小值，我们一般关注High中Used的值，如果达到100%，在GC过程中一定存在内存分配不足的情况，需要调整GC的触发时机，更早或者更快地进行GC。
- **GC信息统计**：可以定时的打印垃圾收集信息，观察10秒内、10分钟内、10个小时内，从启动到现在的所有统计信息。利用这些统计信息，可以排查定位一些异常点。



参考链接

https://zhuanlan.zhihu.com/p/111886882

https://tech.meituan.com/2020/08/06/new-zgc-practice-in-meituan.html