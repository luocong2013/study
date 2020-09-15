package com.zync.stringutils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 01396453(luocong)
 * @version 3.0.3
 * @description 替换部分字符串
 * @date 2020/9/15 9:54
 */
public class StringUtilsOverlay {

    public static void main(String[] args) {
        String telphone = "18080408040";
        System.out.println(StringUtils.overlay(telphone, "******", 3, 9));
    }
}
