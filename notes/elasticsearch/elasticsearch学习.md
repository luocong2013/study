## ElasticSearch

### 一、概述

ElasticSearch是基于Lucene（搜索引擎库）的开源搜索引擎，对外提供一系列基于Java和HTTP的API， 目的是通过简单的RESTful API来隐藏Lucene的复杂性。

具有以下特点：

* 支持全文检索
* 分布式的实时文件存储，每个字段都被索引并可被搜索
* 分布式的实时分析搜索引擎

可以对照关系型数据库来理解ElasticSearch的有关概念

| Relational DB | ElasticSearch |
| ------------- | ------------- |
| Databases     | Indices       |
| Tables        | Types         |
| Rows          | Documents     |
| Columns       | Fields        |

