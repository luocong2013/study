package com.ccyw.lucene.dao.impl;

import com.ccyw.lucene.dao.IndexDao;
import com.ccyw.lucene.bean.Article;
import com.ccyw.lucene.bean.QueryResult;

import java.io.IOException;
import java.util.Collection;

/**
 * @author luoc
 * @version V1.0.0
 * @description 索引Dao实现
 * @date 2018/10/27 21:16
 */
public class IndexDaoImpl implements IndexDao {

    @Override
    public void save(Article... articles) throws IOException {

    }

    @Override
    public void save(Collection<Article> articles) throws IOException {

    }

    @Override
    public void delete(String id) throws IOException {

    }

    @Override
    public void update(Article article) throws IOException {

    }

    @Override
    public QueryResult search(String queryString, int firstResult, int maxResult) throws IOException {
        return null;
    }
}
