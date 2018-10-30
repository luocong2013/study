package com.ccyw.lucene.dao;

import com.ccyw.lucene.bean.Article;
import com.ccyw.lucene.bean.QueryResult;

import java.io.IOException;
import java.util.Collection;

/**
 * @author luoc
 * @version V1.0.0
 * @description 索引Dao
 * @date 2018/10/27 21:10
 */
public interface IndexDao {

    /**
     * 保存数据
     * @param articles
     * @throws IOException
     */
    void save(Article ... articles) throws IOException;

    /**
     * 保存数据
     * @param articles
     * @throws IOException
     */
    void save(Collection<Article> articles) throws IOException;

    /**
     * 删除数据
     * @param id
     * @throws IOException
     */
    void delete(String id) throws IOException;

    /**
     * 更新数据
     * @param article
     * @throws IOException
     */
    void update(Article article) throws IOException;

    /**
     * 查询数据
     * @param queryString
     * @param firstResult
     * @param maxResult
     * @return
     * @throws IOException
     */
    QueryResult search(String queryString, int firstResult, int maxResult) throws IOException;
}
