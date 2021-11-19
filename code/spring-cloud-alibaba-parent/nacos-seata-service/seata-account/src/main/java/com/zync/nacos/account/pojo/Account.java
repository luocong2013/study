package com.zync.nacos.account.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  账户表
 * </p>
 *
 * @author luocong
 * @since 2021-11-17
 */
@Data
@Entity
@Table(name = "account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户userId
     */
    @Column
    private String userId;

    /**
     * 余额，单位分
     */
    @Column
    private Long money;

    /**
     * 创建时间
     */
    @Column
    private Date createTime;


}
