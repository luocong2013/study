package com.zync.nacos.account.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 参数
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/18 13:12
 */
@Data
public class AccountDTO {

    /**
     * 用户ID
     */
    @NotBlank
    private String userId;

    /**
     * 金额
     */
    @NotNull
    private Long money;
}
