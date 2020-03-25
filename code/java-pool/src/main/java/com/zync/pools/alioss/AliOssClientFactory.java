package com.zync.pools.alioss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zync.pools.alioss.client.AliOssInfo;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * AliOssClientFactory
 *
 * @author luocong
 * @version V1.0.0
 * @description 阿里OSS工厂类
 * @date 2020年03月25日 16:46
 */
public class AliOssClientFactory extends BaseKeyedPooledObjectFactory<AliOssInfo, OSS> {

    @Override
    public OSS create(AliOssInfo ossInfo) throws Exception {
        return new OSSClientBuilder().build(ossInfo.getEndpoint(), ossInfo.getAccessKeyId(), ossInfo.getAccessKeySecret());
    }

    @Override
    public PooledObject<OSS> wrap(OSS ossClient) {
        return new DefaultPooledObject<>(ossClient);
    }
}
