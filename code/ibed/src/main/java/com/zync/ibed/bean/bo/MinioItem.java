package com.zync.ibed.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Minio数据项
 * @date 2020/1/11 15:07
 */
@Getter
@Setter
@AllArgsConstructor
public class MinioItem {
    /**
     * objectName
     */
    private String objectName;
    /**
     * 是否是目录
     */
    private boolean isDir;
}
