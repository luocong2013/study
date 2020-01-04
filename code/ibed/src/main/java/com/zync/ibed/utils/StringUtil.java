package com.zync.ibed.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 字符串工具类
 * @date 2020/1/4 15:44
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

    /**
     * <p>如：StringUtil.format("Hello {}, I'm {}.", "张三", "李四"); 将得到`Hello 张三, I'm 李四.`。</p>
     * StringUtil
     * @description 使用 Slf4j 的方式来格式化字符串.
     * @param pattern 字符串模式
     * @param args    不定长的参数值
     * @return 格式化后的字符串
     * @author luocong
     * @date 2020/01/04 15:58
     * @version 1.0.0
     */
    public static String format(String pattern, Object... args) {
        return MessageFormatter.arrayFormat(pattern, args).getMessage();
    }
}
