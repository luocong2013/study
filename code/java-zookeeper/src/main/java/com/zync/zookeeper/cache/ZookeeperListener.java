package com.zync.zookeeper.cache;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.*;
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

        //getWatcherListener(client, "/a");
        //getCuratorWatcherListener(client, "/a");
        //getNodeCacheListener(client, "/a");
        //getPathChildrenCacheListener(client, "/a");
        getTreeCacheListener(client, "/a");

        Thread.currentThread().join();
    }

    public static void getWatcherListener(CuratorFramework client, String path) throws Exception {
        // ① ZK自带 Watcher标准的事件处理器
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
                    getWatcherListener(client, path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).forPath(path);
    }

    public static void getCuratorWatcherListener(CuratorFramework client, String path) throws Exception {
        // ② CuratorWatcher
        client.getData().usingWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent event) throws Exception {
                System.out.println(event);
                getCuratorWatcherListener(client, path);
            }
        }).forPath(path);
    }

    public static void getNodeCacheListener(CuratorFramework client, String path) throws Exception {
        // ③ NodeCache 节点缓存的监听
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

    public static void getPathChildrenCacheListener(CuratorFramework client, String path) throws Exception {
        // ④ PathChildrenCache 子节点监听
        // 只能监听子节点，监听不到当前节点
        // 不能递归监听，子节点下的子节点不能递归监控
        PathChildrenCache childrenCache = new PathChildrenCache(client, path, true);
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
                    System.out.println("其他事件：" + event);
                    break;
            }
        });
    }

    public static void getTreeCacheListener(CuratorFramework client, String path) throws Exception {
        // ⑤ TreeCache可以监控整个树上的所有节点，类似于PathCache和NodeCache的组合
        TreeCache treeCache = new TreeCache(client, path);
        treeCache.getListenable().addListener((c, event) ->
                System.out.println("事件类型：" + event.getType() + " 路径：" + ((event.getData() != null) ? event.getData().getPath() : null))
        );
        treeCache.start();
    }

}
