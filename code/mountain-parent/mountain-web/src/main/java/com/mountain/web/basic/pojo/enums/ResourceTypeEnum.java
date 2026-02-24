package com.mountain.web.basic.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资源类型枚举
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/24 14:12
 **/
@Getter
@AllArgsConstructor
public enum ResourceTypeEnum implements IEnum<Integer> {
    /**
     * 菜单
     */
    MENU(0),
    /**
     * 按钮
     */
    BUTTON(1),
    /**
     * 其他
     */
    OTHER(2)
    ;

    private final Integer value;
}
