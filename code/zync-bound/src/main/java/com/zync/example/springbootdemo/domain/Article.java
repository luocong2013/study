package com.zync.example.springbootdemo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Data
@Document(indexName = "article", type = "_doc", shards = 2, replicas = 0, refreshInterval = "5s")
public class Article {

    @Id
    @Field(store = true, type = FieldType.Integer)
    private Integer id;

    @Field(store = true, type = FieldType.Keyword)
    private String tag;

    @Field(store = true, type = FieldType.Long)
    private Long age;

    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word", store = true, type = FieldType.Text)
    private String title;

    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word", store = true, type = FieldType.Text)
    private String content;

//    @Field(store = true, format = DateFormat.year_month_day, type = FieldType.Date)
//    private Date createTime;
}
