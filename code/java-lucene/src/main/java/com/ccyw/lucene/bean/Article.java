package com.ccyw.lucene.bean;

import com.ccyw.lucene.annotation.LuceneField;
import com.ccyw.lucene.annotation.LuceneIndex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoc
 * @version V1.0.0
 * @description 文档对象
 * @date 2018/10/27 20:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@LuceneIndex(indexName = "D:/lucene/test1")
public class Article {
    /**
     * 编号
     */
    @LuceneField(fieldName = "id")
    private Integer id;
    /**
     * 标题
     */
    @LuceneField(fieldName = "title")
    private String title;
    /**
     * 内容
     */
    @LuceneField(fieldName = "content")
    private String content;
}
