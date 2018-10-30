package com.ccyw.lucene.test;

import com.ccyw.lucene.annotation.LuceneHelper;
import com.ccyw.lucene.bean.Article;
import com.ccyw.lucene.bean.QueryResult;
import org.junit.Test;

/**
 * @author luoc
 * @version V1.0.0
 * @description Lucene测试类
 * @date 2018/10/30 21:50
 */
public class LuceneTest {

    @Test
    public void testInsert() throws Exception {
        System.out.println("*****************检索开始******************");
        long startTime = System.currentTimeMillis();
        Article article = new Article(1, "Google", "成立于1998年9月4日，由拉里·佩奇和谢尔盖·布林共同创建，被公认为全球最大的搜索引擎。谷歌是一家位于美国的跨国科技企业.");
        LuceneHelper.insert(article);
        long endTime = System.currentTimeMillis();
        System.out.println("*****************检索结束******************");
        System.out.println("总共花费"+(endTime-startTime)+"ms");
    }

    @Test
    public void search() {
        try {
            System.out.println("*****************检索开始******************");
            long startTime = System.currentTimeMillis();
            QueryResult result = LuceneHelper.search("搜索引擎", 0, 10);
            for (Article article : result.getArticles()) {
                System.out.println("id：" + article.getId());
                System.out.println("title：" + article.getTitle());
                System.out.println("content：" + article.getContent());
            }
            long endTime = System.currentTimeMillis();
            System.out.println("*****************检索结束******************");
            System.out.println("总共花费"+(endTime-startTime)+"ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        try {
            System.out.println("*****************检索开始******************");
            long startTime = System.currentTimeMillis();
            Article article = new Article(1, "BaiDu ", "成立于1998年9月4日，由拉里·佩奇和谢尔盖·布林共同创建，被公认为全球最大的搜索引擎。谷歌是一家位于美国的跨国科技企业.");
            LuceneHelper.update(article);
            long endTime = System.currentTimeMillis();
            System.out.println("*****************检索结束******************");
            System.out.println("总共花费"+(endTime-startTime)+"ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        try {
            System.out.println("*****************检索开始******************");
            long startTime = System.currentTimeMillis();
            Article article = new Article();
            article.setId(1);
            LuceneHelper.delete(article);
            long endTime = System.currentTimeMillis();
            System.out.println("*****************检索结束******************");
            System.out.println("总共花费"+(endTime-startTime)+"ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
