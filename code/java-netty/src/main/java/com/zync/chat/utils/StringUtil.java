package com.zync.chat.utils;

import lombok.experimental.UtilityClass;
import org.slf4j.helpers.MessageFormatter;

/**
 * 字符串工具类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/8 17:28
 */
@UtilityClass
public class StringUtil {

    /**
     * 使用 Slf4j 的方式来格式化字符串.
     * <p>如：StringUtil.format("Hello {}, I'm {}.", "张三", "李四"); 将得到`Hello 张三, I'm 李四.`。</p>
     *
     * @param pattern 字符串模式
     * @param args    不定长的参数值
     * @return 格式化后的字符串
     */
    public String format(String pattern, Object... args) {
        return MessageFormatter.arrayFormat(pattern, args).getMessage();
    }
}
