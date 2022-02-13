## redis 学习

#### 一、redis命令

```
1. redis执行了make install后，redis的课执行文件都会自动复制到 /usr/local/bin 目录
2. redis-server           redis服务器
3. redis-cli              redis命令行客户端
4. redis-benchmark        redis性能测试工具
5. redis-check-aof        aof文件修复工具
6. redis-check-dump       rdb文件检查工具
```



#### 二、停止redis命令

```shell
> redis-cli shutdown
```



#### 三、启动redis命令

```shell
1. 启动 redis-server
> redis-server

2. 带配置文件启动
> redis-server ./redis.conf

3. 带配置文件启动且指定某几个配置，配置名称前加 --
> redis-server ./redis.conf --daemonize yes --port 7077
```



#### 四、redis命令行客户端

```shell
1. 进入交互模式
> redis-cli -h 127.0.0.1 -p 6379

2. 状态回复，pong表示可用
127.0.0.1:7077> ping
PONG
```

