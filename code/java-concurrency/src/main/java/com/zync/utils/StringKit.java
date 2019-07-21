package com.zync.utils;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 字符串工具类
 * @date 2019/7/21 18:03
 */
public final class StringKit {

    private StringKit() {
    }

    /**
     * 使用 Slf4j 的方式来格式化字符串.
     * <p>如：StringKit.format("Hello {}, I'm {}.", "张三", "李四")，将得到`Hello 张三, I'm 李四.`。</p>
     *
     * @param pattern 字符串模式
     * @param args 不定长的参数值
     * @return 格式化后的字符串
     */
    public static String format(String pattern, Object... args) {
        return MessageFormatter.arrayFormat(pattern, args).getMessage();
    }
}
