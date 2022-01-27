### log4j2异步配置
#### 异步配置步骤
- 添加依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <!-- 去掉springboot默认配置 -->
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<!-- log4j2 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>

<!--log4j2异步配置-->
<dependency>
   <groupId>com.lmax</groupId>
   <artifactId>disruptor</artifactId>
   <version>3.4.4</version>
</dependency>
```

- log4j2-spring.xml 异步配置（方式一）
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration >
    <Appenders>
        <Async name="ASYNC-LOG" bufferSize="10000">
            <AppenderRef ref="WARN-LOG" />
            <AppenderRef ref="ERROR-LOG" />
            <LinkedTransferQueue />
        </Async>
    </Appenders>
    
    <Loggers>
        <Root level="${ROOT_LOG_LEVEL}">
            <AppenderRef ref="CONSOLE-LOG" />
            <AppenderRef ref="ROLLING-LOG" />
            <AppenderRef ref="INFO-LOG" />
            
            <AppenderRef ref="ASYNC-LOG" />
        </Root>
    </Loggers>
</Configuration>
```

- log4j2-spring.xml 异步配置（方式二） 

log4j2-spring.xml配置和同步的保持一致
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration >
    <Loggers>
        <!-- 指定包下的日志输出
        <AsyncLogger name="com.zync.log4j2" additivity="false">
            <AppenderRef ref="WARN-LOG" />
            <AppenderRef ref="ERROR-LOG" />
        </AsyncLogger>
        -->
        
        <Root level="${ROOT_LOG_LEVEL}">
            <AppenderRef ref="CONSOLE-LOG" />
            <AppenderRef ref="ROLLING-LOG" />
            <AppenderRef ref="INFO-LOG" />
            <AppenderRef ref="WARN-LOG" />
            <AppenderRef ref="ERROR-LOG" />
        </Root>
    </Loggers>
</Configuration>
```
以下步骤三选一
1. 添加log4j2.component.properties配置文件

配置文件中添加如下内容
```properties
Log4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
```

2. 修改启动类
```java
@SpringBootApplication
public class SpringBootLog4j2Application {

    public static void main(String[] args) {
        // 下面语句使得日志输出使用异步处理，减少输出日志对性能的影响
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        SpringApplication.run(SpringBootLog4j2Application.class, args);
    }
}
```

3. 增加启动参数
```shell
java -jar -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector xxx.jar
```

- 总结

1. 异步配置（方式一）比较灵活，可以针对不同Appender配置异步，其使用了ArrayBlockingQueue来保存需要异步输出的日志事件
2. 异步配置（方式二）是针对log4j2-spring.xml中所有的Appender做异步配置，其使用了Disruptor框架来实现高吞吐，log4j2官方测试，（方式二）相比（方式一）有更好的表现
3. 异步配置（方式一）与 异步配置（方式二）一般不同时配置

