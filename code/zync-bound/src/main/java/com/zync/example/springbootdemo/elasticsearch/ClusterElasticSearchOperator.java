package com.zync.example.springbootdemo.elasticsearch;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ClusterElasticSearchOperator {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     * @param indexName 索引名
     * @param source    索引source
     * @return
     * @throws IOException
     */
    public boolean createIndex(String indexName, String source) throws IOException {
        if (indexExists(indexName)) {
            deleteIndex(indexName);
        }
        CreateIndexRequest indexRequest = new CreateIndexRequest(indexName);
        indexRequest.source(source, XContentType.JSON);
        return elasticsearchRestTemplate.getClient().indices().create(indexRequest, RequestOptions.DEFAULT).isAcknowledged();
    }

    /**
     * 删除索引
     * @param indexName 索引名
     * @return
     */
    public boolean deleteIndex(String indexName) {
        return elasticsearchRestTemplate.deleteIndex(indexName);
    }

    /**
     * 判断索引是否存在
     * @param indexName 索引名
     * @return
     */
    public boolean indexExists(String indexName) {
        return elasticsearchRestTemplate.indexExists(indexName);
    }

    /**
     * 索引文档
     * @param indexQuery
     * @return
     */
    public String index(IndexQuery indexQuery) {
        return elasticsearchRestTemplate.index(indexQuery);
    }
}
