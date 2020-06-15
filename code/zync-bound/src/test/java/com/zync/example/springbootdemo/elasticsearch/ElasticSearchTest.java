package com.zync.example.springbootdemo.elasticsearch;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.zync.example.springbootdemo.domain.Article;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
public class ElasticSearchTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Autowired
    private ArticleService articleService;

    @Test
    void createIndexTest() throws IOException {
        boolean acknowledged = elasticSearchService.createIndex("text_xx");
        System.out.println("acknowledged: " + acknowledged);
    }

    @Test
    void deleteIndex() {
        boolean acknowledged = elasticSearchService.deleteIndex("text_xx");
        System.out.println("acknowledged: " + acknowledged);
    }

    @Test
    void insertData() throws IOException {
        elasticSearchService.insertData("text_xx");
    }

    @Test
    void searchData() throws IOException {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("baike_content", "analyzer");
        elasticSearchService.search("text_xx", queryBuilder);
    }

    @Test
    void contextLoads() throws IOException {
        IndicesClient indices = elasticsearchRestTemplate.getClient().indices();

//        GetIndexRequest getIndexRequest = new GetIndexRequest();
//        getIndexRequest.indices("edr_*");
//        GetIndexResponse getIndexResponse = indices.get(getIndexRequest, RequestOptions.DEFAULT);
//        String[] strings = getIndexResponse.getIndices();
//        Arrays.stream(strings).forEach(System.out::println);

        GetMappingsRequest getMappingsRequest = new GetMappingsRequest();
        getMappingsRequest.indices("edr_file_create");
        getMappingsRequest.indicesOptions(IndicesOptions.lenientExpandOpen());
        GetMappingsResponse mapping = indices.getMapping(getMappingsRequest, RequestOptions.DEFAULT);
        ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = mapping.getMappings();
        Iterator<ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>> iterator = mappings.iterator();
        while (iterator.hasNext()) {
            ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>> next = iterator.next();
            System.out.println(next.toString());
        }
    }

    @Test
    void createIndex() {
        elasticsearchRestTemplate.createIndex(Article.class);
        elasticsearchRestTemplate.putMapping(Article.class);
    }

    @Test
    void test1() {
        Article article = new Article();
        article.setId(1);
        article.setTitle("张三打牌");
        article.setContent("张三打牌输了300元人民币，没有饭吃了。");
        article.setTag("TEST");
        article.setAge(20L);

        Article article2 = new Article();
        article2.setId(2);
        article2.setTitle("李四偷车");
        article2.setContent("李四偷车赚了300元人民币，吃了个大鸡腿。");
        article2.setTag("TEST");
        article2.setAge(30L);

        Article article3 = new Article();
        article3.setId(3);
        article3.setTitle("王五睡觉");
        article3.setContent("王五睡觉丢了30万元人民币，急的要哭了。");
        article3.setTag("TEST2");
        article3.setAge(40L);

        List<Article> articles = new ArrayList<>();
        articles.add(article);
        articles.add(article2);
        articles.add(article3);
        articleService.batchIndex(articles);
    }

    @Test
    void test2() throws IOException {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("content", "张三");
        elasticSearchService.search("article", matchQuery);
    }
}
