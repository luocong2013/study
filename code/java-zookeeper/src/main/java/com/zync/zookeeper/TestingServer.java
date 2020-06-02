package com.zync.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

/**
 * @author luocong
 * @description
 * @date 2020/6/2 17:15
 */
public class TestingServer {

    public static CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        return CuratorFrameworkFactory.builder()
                .connectString("10.11.1.141:2181")
                // 会话超时时间
                .sessionTimeoutMs(500000)
                // 连接超时时间
                .connectionTimeoutMs(500000)
                .retryPolicy(retryPolicy)
                .build();
    }

    public static void close(CuratorFramework client) {
        CloseableUtils.closeQuietly(client);
    }
}
