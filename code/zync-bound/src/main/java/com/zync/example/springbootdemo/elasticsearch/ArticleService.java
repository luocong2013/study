package com.zync.example.springbootdemo.elasticsearch;

import com.zync.example.springbootdemo.dao.ArticleDao;
import com.zync.example.springbootdemo.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    public void index(Article article) {
        articleDao.save(article);
    }

    public void batchIndex(Collection<Article> articles) {
        articleDao.saveAll(articles);
    }
}
