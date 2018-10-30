package com.ccyw.lucene.annotation;

import com.ccyw.lucene.bean.Article;
import com.ccyw.lucene.bean.QueryResult;
import com.ccyw.lucene.common.Commons;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description Lucene 对象模式访问实现类
 * @date 2018/10/27 23:10
 */
public class LuceneHelper {

    private LuceneHelper(){
    }

    /**
     * 插入一条数据
     * @param obj
     * @param <T>
     * @throws LuceneException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static <T> void insert(T obj) throws LuceneException, IllegalAccessException, IOException {
        if (obj == null) {
            return;
        }
        // 创建集合文档
        List<Document> documents = new ArrayList<>();
        // 索引名
        String indexName = getOrmIndexName(obj.getClass());
        Document document = LuceneBeanTrans.bean2Document(obj);
        documents.add(document);
        if (CollectionUtils.isNotEmpty(documents)) {
            saveDocuments(documents, indexName);
        }
    }

    /**
     * 批量保存数据
     * @param objs
     * @param <T>
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static <T> void batchInsert(List<T> objs) throws IllegalAccessException, IOException {
        if (CollectionUtils.isEmpty(objs)) {
            return;
        }
        // 创建集合文档
        List<Document> documents = new ArrayList<>();
        // 索引名
        String indexName = getOrmIndexName(objs.get(0).getClass());
        for (T obj : objs) {
            if (obj == null) {
                continue;
            }
            Document document = LuceneBeanTrans.bean2Document(obj);
            documents.add(document);
        }
        if (CollectionUtils.isNotEmpty(documents)) {
            saveDocuments(documents, indexName);
        }
    }

    /**
     * 按照 id 删除索引
     * @param obj
     * @param <T>
     * @throws Exception
     */
    public static <T> void delete(T obj) throws Exception {
        if (obj == null) {
            return;
        }
        // 索引名
        String indexName = getOrmIndexName(obj.getClass());
        IndexWriter indexWriter = LuceneRunner.getIndexWriter(indexName);
        Term term = LuceneBeanTrans.bean2Term(obj, "id");
        indexWriter.deleteDocuments(term);
        indexWriter.commit();
    }

    /**
     * 修改索引
     * @param obj
     * @param <T>
     * @throws Exception
     */
    public static <T> void update(T obj) throws Exception {
        if (obj == null) {
            return;
        }
        // 索引名
        String indexName = getOrmIndexName(obj.getClass());
        IndexWriter indexWriter = LuceneRunner.getIndexWriter(indexName);
        Document document = LuceneBeanTrans.bean2Document(obj);
        Term term = LuceneBeanTrans.bean2Term(obj, "id");
        //先删除，后创建
        indexWriter.updateDocument(term, document);
        indexWriter.commit();
    }

    /**
     * 保存数据
     * @param documents
     * @param index
     * @throws IOException
     */
    private static void saveDocuments(Iterable<Document> documents, String index) throws IOException {
        IndexWriter indexWriter = LuceneRunner.getIndexWriter(index);
        indexWriter.addDocuments(documents);
        indexWriter.commit();
    }

    /**
     * 获取索引名
     * @param clazz
     * @return
     */
    private static String getOrmIndexName(Class<?> clazz) {
        String indexName = clazz.getAnnotation(LuceneIndex.class).indexName();
        return indexName;
    }

    /**
     * 查询索引
     * @param queryString
     * @param firstReuslt
     * @param maxResult
     * @return
     * @throws Exception
     */
    public static QueryResult search(String queryString, int firstReuslt, int maxResult) throws Exception {
        List<Article> list = new ArrayList<Article>();
        DirectoryReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("D:/lucene/test1")));
        //创建搜索器
        IndexSearcher searcher = new IndexSearcher(reader);
        //类似SQL进行关键字查询
        String[] fields = {"id", "title", "content"};
        /**
         * 建立查询解析器
         * 第一个参数是要查询的字段
         * 第二个参数是分析器analyzer
         * QueryParser parser = new QueryParser("content", analyzer);
         */
        QueryParser parser = new MultiFieldQueryParser(fields, Commons.analyzer);
        //根据传入的queryString查找
        Query query = parser.parse(queryString);
        /**
         * 开始查询
         * 第一个参数是通过传过来的参数查找得到的query
         * 第二个参数是要查询的行数
         */
        TopDocs topDocs = searcher.search(query, firstReuslt+maxResult);
        long count = topDocs.totalHits;//总记录数
        System.out.println("总记录数为：" + count);
        ScoreDoc[] hits = topDocs.scoreDocs;

        /*************高亮显示START*************/
        //算分
        QueryScorer scorer = new QueryScorer(query);
        //显示得分高的片段
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        /**
         * 设置标签内部关键字的颜色
         * 第一个参数：标签的前半部分
         * 第二个参数：标签的后半部分
         */
        Formatter formatter = new SimpleHTMLFormatter("<b><font color='read'>", "</font></b>");
        /**
         * 第一个参数：对查询结果进行实例化
         * 第二个参数：片段得分（显示得分高的片段，即摘要）
         */
        Highlighter highlighter = new Highlighter(formatter, scorer);
        //设置片段
        highlighter.setTextFragmenter(fragmenter);
        /*************高亮显示END*************/

        //处理结果
        int endIndex = Math.min(firstReuslt+maxResult, hits.length);
        for (int i = firstReuslt; i < endIndex; i++) {
            Document hitDoc = searcher.doc(hits[i].doc);
            Article article = LuceneBeanTrans.document2Article(hitDoc);
            String text = highlighter.getBestFragment(Commons.analyzer, "content", hitDoc.get("content"));
            if (text != null) {
                article.setContent(text);
            }
            list.add(article);
        }
        reader.close();
        return new QueryResult(count, list);
    }
}
