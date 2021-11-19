package com.zync.nacos.storage.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 参数
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/18 13:05
 */
@Data
public class ProductDTO {

    /**
     * 产品ID
     */
    @NotNull
    private Long id;

    /**
     * 购买数量
     */
    @NotNull
    private Long amount;
}
