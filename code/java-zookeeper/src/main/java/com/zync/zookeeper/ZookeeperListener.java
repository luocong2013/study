package com.zync.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author luocong
 * @description zookeeper监听器
 * @date 2020/6/2 10:43
 */
public class ZookeeperListener {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("10.11.1.141:2181")
                // 会话超时时间
                .sessionTimeoutMs(500000)
                // 连接超时时间
                .connectionTimeoutMs(500000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();

        //getDataListener(client, "/a");
        //getDataListener2(client, "/a");
        getNodeCacheListener(client, "/a");

        Thread.currentThread().join();
    }


    public static void getDataListener(CuratorFramework client, String path) throws Exception {
        client.getData().usingWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent event) throws Exception {
                System.out.println(event);
                getDataListener(client, path);
            }
        }).forPath(path);
    }

    public static void getDataListener2(CuratorFramework client, String path) throws Exception {
        client.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                String path = event.getPath();
                System.out.print("路径： " + path);
                switch (event.getType()) {
                    case NodeCreated:
                        System.out.println("创建节点");
                        break;
                    case NodeDeleted:
                        System.out.println("删除节点");
                        break;
                    case NodeDataChanged:
                        System.out.println("节点数据变化");
                        break;
                    case NodeChildrenChanged:
                        System.out.println("子节点变化");
                        break;
                    default:
                        System.out.println("其他事件");
                }
                try {
                    getDataListener2(client, path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).forPath(path);
    }

    public static void getNodeCacheListener(CuratorFramework client, String path) throws Exception {
        // ② NodeCache 节点缓存的监听
        // 最后一个参数表示是否进行压缩
        NodeCache nodeCache = new NodeCache(client, path, false);
        // true代表缓存当前节点
        nodeCache.start(true);
        // 只会监听节点的创建和修改，删除不会监听
        nodeCache.getListenable().addListener(() -> {
            System.out.println("Node data is changed, new data: " + new String(nodeCache.getCurrentData().getData()));
        });

        //StandardListenerManager<NodeCacheListener> manager = StandardListenerManager.standard();
        //manager.addListener(() -> {
        //    System.out.println("Node data is changed, new data: " + new String(nodeCache.getCurrentData().getData()));
        //});

    }

}
