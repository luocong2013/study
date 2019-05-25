package com.zync.ui.common.consts;

import com.zync.ui.common.utils.PropertiesUtil;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 常量类
 * @date 2019/5/25 17:22
 */
public interface Const {

    /**
     * 版本号
     */
    String VERSION = PropertiesUtil.get("commons.io.version");
    /**
     * UTF-8编码
     */
    String ENCOD_UTF_8 = "UTF-8";
}
