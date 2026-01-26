package com.mountain.common.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 删除状态，0-正常，其他-删除
 *
 * @author luocong
 * @version v1.0
 * @since 2023/11/8 16:52
 */
@Getter
@RequiredArgsConstructor
public enum DeletedEnum {
    /**
     * 正常
     */
    NORMAL("0")
    ;

    @EnumValue
    private final String value;
}
