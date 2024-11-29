package com.customzied.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * MybatisPlus Entity 基础类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/11/6 18:30
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BaseEntity<T extends Model<T>> extends Model<T> {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 最新修改人
     */
    private String modifier;

    /**
     * 备注
     */
    private String remark;

    /**
     * 使用Long类型，才能使用函数作为逻辑删除默认值，数据库字段为varchar
     */
    @JsonIgnore
    @TableLogic
    @TableField(select = false)
    private Long deleted;
}
