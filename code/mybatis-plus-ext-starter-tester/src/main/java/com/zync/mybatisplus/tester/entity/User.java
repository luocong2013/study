package com.zync.mybatisplus.tester.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zync.boot.mybatisplus.ext.core.annotation.Default;
import com.zync.boot.mybatisplus.ext.core.annotation.IgnoreInsert;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author luocong
 * @since 2022-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user`")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 姓名
     */
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String email;

    /**
     * 创建时间
     */
    @Default
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @IgnoreInsert
    private LocalDateTime updateTime;

    /**
     * 0-未删除，其他值-删除
     */
    @Default(stringValue = "123")
    @TableLogic
    @TableField(select = false, fill = FieldFill.INSERT)
    private String deleted;


}
