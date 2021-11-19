package com.zync.nacos.order.pojo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  订单表
 * </p>
 *
 * @author luocong
 * @since 2021-11-17
 */
@Getter
@Setter
@Entity
@Table(name = "t_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品Id
     */
    @Column
    private Long productId;

    /**
     * 数量
     */
    @Column
    private Long num;

    /**
     * 用户唯一Id
     */
    @Column
    private String userId;

    /**
     * 创建时间
     */
    @Column
    private Date createTime;

    /**
     * 订单状态 1 未付款 2 已付款 3 已完成
     */
    @Column
    private Integer status;

    public Order() {
    }

    public Order(Long productId, Long num, String userId, Date createTime, Integer status) {
        this.productId = productId;
        this.num = num;
        this.userId = userId;
        this.createTime = createTime;
        this.status = status;
    }
}
