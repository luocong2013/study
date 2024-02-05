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
-Xlog:gc*=info,gc+stats=warning:file=logs/xycrm_agent_gc_%t.log:time,level,tags,hostname,pid,tid:filecount=5,filesize=100M
```





参考链接

https://zhuanlan.zhihu.com/p/111886882