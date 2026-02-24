package com.mountain.web.basic.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资源请求类型枚举
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/24 14:14
 **/
@Getter
@AllArgsConstructor
public enum RequestTypeEnum implements IEnum<String> {
    GET,
    POST,
    PUT,
    DELETE,
    NONE
    ;

    @Override
    public String getValue() {
        return name();
    }
}
