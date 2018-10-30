package com.ccyw.lucene.annotation;

import com.ccyw.lucene.common.Commons;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luoc
 * @version V1.0.0
 * @description Lucene工具类 对API的简单封装
 * @date 2018/10/27 23:15
 */
public class LuceneRunner {

    private LuceneRunner() {
    }

    /**
     * 中文分词器
     */
    private static final IndexWriterConfig INDEX_WRITER_CONFIG = new IndexWriterConfig(Commons.analyzer);

    /**
     * 缓存
     */
    private static Map<String, IndexWriter> indexMap = new HashMap<>(16);

    private static class Builder {
        private static final LuceneRunner INSTANCE = new LuceneRunner();
    }

    /**
     * 获取索引库
     * @param index
     * @return
     */
    public static IndexWriter getIndexWriter(String index) {
        if (!indexMap.containsKey(index)) {
            Builder.INSTANCE.initIndex(index);
        }
        return indexMap.get(index);
    }

    /**
     * 初始化索引库
     * @param index
     */
    private synchronized void initIndex(String index) {
        try {
            Directory directory = FSDirectory.open(Paths.get(index));
            IndexWriter indexWriter = new IndexWriter(directory, INDEX_WRITER_CONFIG);
            indexMap.put(index, indexWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
