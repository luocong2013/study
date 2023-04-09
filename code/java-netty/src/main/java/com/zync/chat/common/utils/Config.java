package com.zync.chat.common.utils;

import com.zync.chat.common.consts.Const;
import com.zync.chat.protocol.Algorithm;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * 获取配置内容类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 17:40
 */
@UtilityClass
public class Config {

    /**
     * 获取序列化算法
     *
     * @return
     */
    public Algorithm getSerializerAlgorithm() {
        String value = PropertiesUtil.get(Const.SERIALIZER_ALGORITHM_KEY);
        if (StringUtils.isBlank(value)) {
            return Algorithm.Java;
        }
        return Algorithm.valueOf(value);
    }

}
