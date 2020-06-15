package com.zync.example.springbootdemo.dao;

import com.zync.example.springbootdemo.domain.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleDao extends ElasticsearchRepository<Article, Integer> {
}
