package com.mountain.common.base.vo;

import lombok.*;

/**
 * 公钥VO
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/28 15:50
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PublicKeyVO {

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 票据
     */
    private String ticket;
}
