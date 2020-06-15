package com.zync.swx.hutool;


import cn.hutool.core.io.resource.ResourceUtil;

import java.net.URL;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.hutool
 * @description TODO
 * @date 2018-1-2 10:21
 */
public class UtilDemo {

    public static void main(String[] args) {
        URL url = ResourceUtil.getResource("起诉意见书训练集");
        System.out.println(url.getPath());
    }
}
