package com.mountain.common.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * MybatisPlus 简单 Entity
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/10 15:35
 **/
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class SimpleEntity<T extends Model<T>> extends Model<T> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
}
