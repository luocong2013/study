package com.ccyw.lucene.common;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;

/**
 * @author luoc
 * @version V1.0.0
 * @description 常量接口
 * @date 2018/10/30 22:31
 */
public interface Commons {

    /**
     * 中文分词器
     */
    Analyzer analyzer = new SmartChineseAnalyzer();
}
