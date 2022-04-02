## seata资料

[官方文档](https://seata.io/zh-cn/index.html)

### seata server、client关注属性


| server                    | client                                                       |
| ------------------------- | ------------------------------------------------------------ |
| registry.type             | registry.type                                                |
| config.type               | config.type                                                  |
| store.mode: file,db,redis | config.type: file、nacos 、apollo、zk、consul、etcd3、custom |
| #only db:                 | service.default.grouplist                                    |
| store.db.driverClassName  | service.vgroupMapping.my_test_tx_group                       |
| store.db.url              | service.disableGlobalTransaction                             |
| store.db.user             |                                                              |
| store.db.password         |                                                              |



### 公共部分

| key                     | desc                           | remark                                                       |
| ----------------------- | ------------------------------ | ------------------------------------------------------------ |
| transport.serialization | client和server通信编解码方式   | seata(ByteBuf)、protobuf、kryo、hession、fst，默认seata      |
| transport.compressor    | client和server通信数据压缩方式 | none、gzip，默认none                                         |
| transport.heartbeat     | client和server通信心跳检测开关 | 默认true开启                                                 |
| registry.type           | 注册中心类型                   | 默认file，支持file 、nacos 、eureka、redis、zk、consul、etcd3、sofa、custom |
| config.type             | 配置中心类型                   | 默认file，支持file、nacos 、apollo、zk、consul、etcd3、custom |



### seata server

[download seata server](https://github.com/seata/seata/releases)

seata server 主要的配置文件是 `conf/registry.conf` 配置内容如下

```config
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "nacos" // 使用nacos作为注册中心

  nacos {
    application = "seata-server"
    serverAddr = "127.0.0.1:8848"
    group = "SEATA_GROUP"
    namespace = "8b9bad7f-d58e-4f76-95f5-780aa8bdc9b3"
    cluster = "default"
    username = "nacos"
    password = "nacos"
  }
  eureka {
    serviceUrl = "http://localhost:8761/eureka"
    application = "default"
    weight = "1"
  }
  redis {
    serverAddr = "localhost:6379"
    db = 0
    password = ""
    cluster = "default"
    timeout = 0
  }
  zk {
    cluster = "default"
    serverAddr = "127.0.0.1:2181"
    sessionTimeout = 6000
    connectTimeout = 2000
    username = ""
    password = ""
  }
  consul {
    cluster = "default"
    serverAddr = "127.0.0.1:8500"
    aclToken = ""
  }
  etcd3 {
    cluster = "default"
    serverAddr = "http://localhost:2379"
  }
  sofa {
    serverAddr = "127.0.0.1:9603"
    application = "default"
    region = "DEFAULT_ZONE"
    datacenter = "DefaultDataCenter"
    cluster = "default"
    group = "SEATA_GROUP"
    addressWaitTime = "3000"
  }
  file {
    name = "file.conf"
  }
}

config {
  # file、nacos 、apollo、zk、consul、etcd3
  type = "nacos" // 使用nacos作为配置中心

  nacos {
    serverAddr = "127.0.0.1:8848"
    namespace = "8b9bad7f-d58e-4f76-95f5-780aa8bdc9b3"
    group = "SEATA_GROUP"
    username = "nacos"
    password = "nacos"
  }
  consul {
    serverAddr = "127.0.0.1:8500"
    aclToken = ""
  }
  apollo {
    appId = "seata-server"
    ## apolloConfigService will cover apolloMeta
    apolloMeta = "http://192.168.1.204:8801"
    apolloConfigService = "http://192.168.1.204:8080"
    namespace = "application"
    apolloAccesskeySecret = ""
    cluster = "seata"
  }
  zk {
    serverAddr = "127.0.0.1:2181"
    sessionTimeout = 6000
    connectTimeout = 2000
    username = ""
    password = ""
    nodePath = "/seata/seata.properties"
  }
  etcd3 {
    serverAddr = "http://localhost:2379"
  }
  file {
    name = "file.conf"
  }
}

```



推送`config.txt`文件到nacos配置中心

```bash
sh ${SEATAPATH}/script/config-center/nacos/nacos-config.sh -h localhost -p 8848 -g SEATA_GROUP -t 5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca -u username -w password
```



`config.txt`文件中需要添加以下内容

```
service.vgroupMapping.seata-storage-tx-group=default
service.vgroupMapping.seata-account-tx-group=default
service.vgroupMapping.seata-order-tx-group=default

以上几个配置分别对应 seata-storage、seata-account、seata-order 服务的事务分组
seata:
	tx-service-group: seata-storage-tx-group
```

