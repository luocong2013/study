package com.zync.zookeeper.count;

import com.google.common.collect.Lists;
import com.zync.zookeeper.TestingServer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.state.ConnectionState;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @description 分布式int计数器 - SharedCount
 * @date 2020/6/2 17:47
 */
public class SharedCounterDemo implements SharedCountListener {

    private static final int QTY = 5;
    private static final String PATH = "/examples/counter";

    public static void main(String[] args) throws IOException, Exception {
        final Random rand = new Random();
        SharedCounterDemo example = new SharedCounterDemo();
        try {
            CuratorFramework client = TestingServer.getClient();
            client.start();

            SharedCount baseCount = new SharedCount(client, PATH, 0);
            baseCount.addListener(example);
            baseCount.start();

            List<SharedCount> examples = Lists.newArrayList();
            ExecutorService service = Executors.newFixedThreadPool(QTY);
            for (int i = 0; i < QTY; ++i) {
                final SharedCount count = new SharedCount(client, PATH, 0);
                examples.add(count);
                Callable<Void> task = () -> {
                    count.start();
                    Thread.sleep(rand.nextInt(10000));
                    System.out.println("Increment:" + count.trySetCount(count.getVersionedValue(), count.getCount() + rand.nextInt(10)));
                    return null;
                };
                service.submit(task);
            }

            service.shutdown();
            service.awaitTermination(10, TimeUnit.MINUTES);

            for (int i = 0; i < QTY; ++i) {
                examples.get(i).close();
            }
            baseCount.close();
        } finally {
            // ignore
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void countHasChanged(SharedCountReader sharedCount, int newCount) throws Exception {
        System.out.println("Counter's value is changed to " + newCount);
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        System.out.println("State changed: " + newState.toString());
    }
}
