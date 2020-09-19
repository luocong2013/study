package com.zync.boot.redistools.utils;

import cn.hutool.core.util.IdUtil;
import lombok.experimental.UtilityClass;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author luocong
 * @version V1.0.0
 * @descrption 字符串工具类
 * @date 2020/9/19 15:21
 */
@UtilityClass
public class StringUtil {

    /**
     * <p>如：StringUtil.format("Hello {}, I'm {}.", "张三", "李四"); 将得到`Hello 张三, I'm 李四.`。</p>
     * StringUtil
     * @description 使用 Slf4j 的方式来格式化字符串.
     * @param pattern 字符串模式
     * @param args    不定长的参数值
     * @return 格式化后的字符串
     * @author luocong
     * @date 2020/09/19 15:24
     * @version V1.0.0
     */
    public static String format(String pattern, Object... args) {
        return MessageFormatter.arrayFormat(pattern, args).getMessage();
    }

    /**
     * 生成UUID
     * @return
     */
    public static String uuid() {
        return IdUtil.fastSimpleUUID();
    }

    /**
     * 雪花算法生成的分布式ID
     * @return
     */
    public static String snowflake() {
        return IdUtil.getSnowflake(1, 1).nextIdStr();
    }
}
