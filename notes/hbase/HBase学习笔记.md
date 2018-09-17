## <center>HBase学习笔记</center>
### 一、HBase简介
1. HBase是开源的、分布式、版本化、非关系型数据库。在Hadoop和HDFS之上提供的类BigTable功能。
  > Hadoop数据库，分布式、可伸缩、大数据存储。
  > 适用于对大数据的随机实时读写操作。
  > 十亿行 * 百万列。
  > 面向列（族）的数据库。

### 二、HBase特性
1. 线性和模块可伸缩
2. 严格一致性读写
3. 表切割自动化以及可配
4. 在区域服务器间的自动容灾

### 三、使用场景
1. 频繁写入
2. 快速随机访问

### 四、HBase安装
1. 预安装jdk、ssh免密、hadoop等等环境（此处省略）
2. tar /home/luoc/hbase-1.2.4.tar.gz -C /home/luoc
3. HBase环境变量
 > HBASE_HOME=/home/luoc/hbase
 > PATH=$PATH:$HBASE_HOME/bin

4. 验证
 > hbase version

### 五、配置HBase
1. local/standalone模式
 a) 配置JAVA_HOME环境变量（可选）
   [hbase/conf/hbase-env.sh]
   export JAVA_HOME=/usr/local/java/jdk1.8
 b) 配置hbase本地目录
   [hbase/conf/hbase-site.xml]
   ```xml
   <property>
   	 <name>hbase.rootdir</name>
     <value>file:///home/luoc/hbase</value>
   </property>
   ```
 c) 配置zk本地数据存放目录
   [hbase/conf/hbase-site.xml]
   ```xml
   <property>
   	 <name>hbase.zookeeper.property.dataDir</name>
     <value>/home/luoc/hbase/zk</value>
   </property>
   ```

2. 伪分布式
3. 完全分布式