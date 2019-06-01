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

**说明：**

1. ElasticSearch可以包含多个索引（Indices），每个索引可以包含多个类型（Types），每个类型包含多个文档（Documents），每个文档包含多个字段（Fields）。
2. ElasticSearch5.x及以前的版本支持多Type；ElasticSearch6.x新建立的索引只支持一个Type；ElasticSearch7.x没有Type的概念了，包括API层面的，使用的是默认的_doc作为Type；据官方说，Type会在ElasticSearch8.x版本彻底移除。因为ElasticSearch底层存储时，不同Type的相同字段会存储在一起，比如：有一个索引叫blog，其中type为user的deleted字段类型是boolean，又想在type为articles的deleted创建字段类型为date，这是不允许的。因为在Lucene底层实现上，同一索引的不同type中的相同字段存储结构都是相同的。

#### 1）索引

索引只是一个把一个或多个分片分组在一起的逻辑空间。可以把索引看成关系型数据库的表。然而，索引的结构是为快速有效的全文索引准备的，特别是它不存储原始值。
Elasticsearch可以把索引存放在一台机器或者分散在多台服务器上，每个索引有一或多个分片（shard），每个分片可以有多个副本（replica）。发送一个新的文档给集群时，你指定一个目标索引并发送给它的任意一个节点。这个节点知道目标索引有多少分片，并且能够确定哪个分片应该用来存储你的文档。可以更改Elasticsearch的这个行为。现在你需要记住的重要信息是，Elasticsearch使用文档的唯一标识符来计算文档应该被放到哪个分片中。索引请求发送到一个节点后，该节点会转发文档到持有相关分片的目标节点中。