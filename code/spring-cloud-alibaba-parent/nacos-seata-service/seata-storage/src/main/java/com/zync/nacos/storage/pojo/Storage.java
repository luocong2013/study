package com.zync.nacos.storage.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  库存表
 * </p>
 *
 * @author luocong
 * @since 2021-11-17
 */
@Data
@Entity
@Table(name = "storage")
public class Storage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @Column
    private String name;

    /**
     * 数量
     */
    @Column
    private Long num;

    /**
     * 创建时间
     */
    @Column
    private Date createTime;

    /**
     * 单价，单位分
     */
    @Column
    private Long price;
}
