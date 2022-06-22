package com.zync.enums;

import java.util.Arrays;

/**
 * 加/解密类型
 *
 * @author luocong
 * @version v1.0
 * @date 2022/6/22 12:58
 */
public enum TypeEnum {
    /**
     * 加密
     */
    ENC("enc", "加密"),
    /**
     * 解密
     */
    DEC("dec", "解密")
    ;

    /**
     * 类型
     */
    private final String type;
    /**
     * 描述
     */
    private final String desc;

    TypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 获取加/解密类型
     * @param type
     * @return
     */
    public static TypeEnum obtainType(final String type) {
        return Arrays.stream(TypeEnum.values())
                .filter(item -> item.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("无此类型[%s]", type)));
    }
}
