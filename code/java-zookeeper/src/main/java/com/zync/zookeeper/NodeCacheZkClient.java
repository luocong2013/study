package com.zync.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption NodeCache：监听节点的新增、修改操作
 * @date 2020/5/24 20:30
 */
public class NodeCacheZkClient {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("192.168.0.111:2181")
                // 会话超时时间
                .sessionTimeoutMs(5000)
                // 连接超时时间
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                // 包含隔离名称
                .namespace("base")
                .build();
        client.start();

        // 最后一个参数表示是否进行压缩
        NodeCache nodeCache = new NodeCache(client, "/super", false);
        nodeCache.start(true);
        // 只会监听节点的创建和修改，删除不会监听
        nodeCache.getListenable().addListener(() -> {
            System.out.println("Node data is changed, new data: " + new String(nodeCache.getCurrentData().getData()));
        });

        PathChildrenCache childrenCache = new PathChildrenCache(client, "/test_PERSISTENT", true);
        childrenCache.start();
        childrenCache.getListenable().addListener((c, event) -> {
            switch (event.getType()) {
                case CHILD_ADDED:
                    System.out.println("Child Add: " + event.getData().getPath());
                    break;
                case CHILD_REMOVED:
                    System.out.println("child Remove: " + event.getData().getPath());
                    break;
                case CHILD_UPDATED:
                    System.out.println("child update: " + event.getData().getPath());
                    break;
                default:
                    break;
            }
        });
    }
}
