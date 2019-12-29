package com.yd.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Zeb灬D on 2017/12/29
 * Description:
 */
public class TestCurator {

    private static final String PATH = "/yd/test/basic";
    private static final String ZK_HOST = "127.0.0.1:2181";

    public static void main(String[] args) throws Exception {
        TestingServer server = new TestingServer();
        CuratorFramework client = null;
        try {
            client = createSimple(server.getConnectString());
//            client.start();
//            client.create().creatingParentsIfNeeded().forPath(PATH, "test".getBytes());
//            CloseableUtils.closeQuietly(client);

//            client = createWithOptions(server.getConnectString(), new ExponentialBackoffRetry(1000, 3), 1000, 1000);
//            System.out.println(new String(client.getData().forPath(PATH)));
            // setListenterThreeThree(client);
//            client.getCuratorListenable().addListener(new CuratorListener() {
//                @Override
//                public void eventReceived(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
//                    System.out.println("");
//                    System.out.println("");
//                }
//            });
            client.start();
            setListenterThreeThree(client);
            while (true) {
                Thread.sleep(1000);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // CloseableUtils.closeQuietly(client);
            //CloseableUtils.closeQuietly(server);
        }

    }


    //3.Tree Cache
    // 监控 指定节点和节点下的所有的节点的变化--无限监听  可以进行本节点的删除(不在创建)
    public static void setListenterThreeThree(CuratorFramework client) throws Exception {
        //ExecutorService pool = Executors.newCachedThreadPool();
//        //设置节点的cache
//        TreeCache treeCache = new TreeCache(client, "/aaa");
//        //设置监听器和处理过程
//        treeCache.getListenable().addListener(new TreeCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
//                ChildData data = event.getData();
//                if(data !=null){
//                    switch (event.getType()) {
//                        case NODE_ADDED:
//                            System.out.println("NODE_ADDED : "+ data.getPath() +"  数据:"+ new String(data.getData()));
//                            break;
//                        case NODE_REMOVED:
//                            System.out.println("NODE_REMOVED : "+ data.getPath() +"  数据:"+ new String(data.getData()));
//                            break;
//                        case NODE_UPDATED:
//                            System.out.println("NODE_UPDATED : "+ data.getPath() +"  数据:"+ new String(data.getData()));
//                            break;
//
//                        default:
//                            break;
//                    }
//                }else{
//                    System.out.println( "data is null : "+ event.getType());
//                }
//            }
//        });
//        //开始监听
//        treeCache.start();

    }

    public static CuratorFramework createSimple(String connectionString) {
        // these are reasonable arguments for the ExponentialBackoffRetry.
        // The first retry will wait 1 second - the second will wait up to 2 seconds - the
        // third will wait up to 4 seconds.
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // The simplest way to get a CuratorFramework instance. This will use default values.
        // The only required arguments are the connection string and the retry policy
        return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
    }

    public static CuratorFramework createWithOptions(String connectionString, RetryPolicy retryPolicy, int connectionTimeoutMs, int sessionTimeoutMs) {
        // using the CuratorFrameworkFactory.builder() gives fine grained control
        // over creation options. See the CuratorFrameworkFactory.Builder javadoc details
        return CuratorFrameworkFactory.builder().connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                // etc. etc.
                .build();
    }

    public static class TestingServer implements Closeable {
        public String getConnectString() {
            return ZK_HOST;
        }

        @Override
        public void close() throws IOException {

        }
    }
}