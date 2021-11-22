package com.zync.nacos.mutiple.datasource.common.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 下单请求 VO
 *
 * @author HelloWoodes
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequestVO {

    /**
     * 用户ID
     */
    @NotNull
    private Long userId;

    /**
     * 产品ID
     */
    @NotNull
    private Long productId;

    /**
     * 产品金额
     */
    @NotNull
    private Integer price;
}
