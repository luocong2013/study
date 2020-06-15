package com.zync.example.springbootdemo.elasticsearch;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ElasticSearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    private static Map<Integer, String> initMap = new HashMap<>(16);
    static {
        initMap.put(1, "The 2 QUICK Brown-Foxes jumped over the lazy dog's bone.");
        initMap.put(2, "The standard analyzer is the default analyzer which is used if none is specified.");
        initMap.put(3, "It provides grammar based tokenization");
        initMap.put(4, "based on the Unicode Text Segmentation algorithm");
        initMap.put(5, "The standard analyzer accepts the following parameters");
    }

    public boolean createIndex(String indexName) throws IOException {
        String source = FileUtils.readFileToString(FileUtils.getFile("F:/罗聪/es/mappings.txt"), StandardCharsets.UTF_8);
        CreateIndexRequest indexRequest = new CreateIndexRequest(indexName);
        indexRequest.source(source, XContentType.JSON);
        return elasticsearchRestTemplate.getClient().indices().create(indexRequest, RequestOptions.DEFAULT).isAcknowledged();
    }

    public boolean deleteIndex(String indexName) {
        return elasticsearchRestTemplate.deleteIndex(indexName);
    }

    public void insertData(String indexName) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < 5; i++) {
            IndexRequest indexRequest = new IndexRequest(indexName, "info");

            Map<String, Object> map = new HashMap<>(16);
            map.put("baike_title", "标题_" + i);
            map.put("baike_link", "链接_" + i);
            map.put("baike_content", initMap.get(i + 1));

            indexRequest.source(map, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        elasticsearchRestTemplate.getClient().bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    public void search(String indexName, QueryBuilder queryBuilder) throws IOException {
        SearchRequest searchRequest = buildSearchRequest(queryBuilder, 0, 5, null, null, null, indexName);
        SearchResponse searchResponse = elasticsearchRestTemplate.getClient().search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();
        float maxScore = hits.getMaxScore();
        System.out.println("总共：" + totalHits.value + " 条，最大得分：" + maxScore);
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            highlightFields.forEach((k, v) -> System.out.println("key -> " + k + ", value -> " + Arrays.toString(v.getFragments())));
        }
    }

    private SearchRequest buildSearchRequest(QueryBuilder queryBuilder, Integer from, Integer size,
                                             String[] includes, String[] excludes, SortBuilder sortBuilder, String... indices) {
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        if (from != null) {
            searchSourceBuilder.from(from);
        }
        if (size != null) {
            searchSourceBuilder.size(size);
        }
        if (sortBuilder != null) {
            searchSourceBuilder.sort(sortBuilder);
        }
        searchSourceBuilder.fetchSource(includes, excludes);

        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("content");
        highlightContent.highlighterType("unified");
        highlightContent.preTags("<font color='red'>");
        highlightContent.postTags("</font>");
        highlightBuilder.field(highlightContent);

        searchSourceBuilder.highlighter(highlightBuilder);

        // 聚合
        AggregationBuilder aggregation = AggregationBuilders.avg("avg_id").field("id.keyword");
        searchSourceBuilder.aggregation(aggregation);


        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }
}
