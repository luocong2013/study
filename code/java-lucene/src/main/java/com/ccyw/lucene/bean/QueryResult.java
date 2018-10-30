package com.ccyw.lucene.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author luoc
 * @version V1.0.0
 * @description 查询结果集
 * @date 2018/10/27 21:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryResult {
    /**
     * 结果数量
     */
    private long count;
    /**
     * 结果集
     */
    private Collection<Article> articles;
}
