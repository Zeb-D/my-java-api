package com.yd.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by Zeb灬D on 2017/12/22.
 */
public class ZkConfigProfile {
    private CuratorFramework client;
    private int sessionTimeoutMs = 100;
    private int connectionTimeoutMs = 100;
    private int baseSleepTimeMs = 100;
    private int maxRetries = 3;

    private String rootNode;

    public ZkConfigProfile(String zkServers, String rootNode) {
//		RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
//		client = CuratorFrameworkFactory.builder().connectString(zkServers).retryPolicy(retryPolicy)
//				.sessionTimeoutMs(sessionTimeoutMs).connectionTimeoutMs(connectionTimeoutMs).build();
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // The simplest way to get a CuratorFramework instance. This will use default values.
        // The only required arguments are the connection string and the retry policy
        client = CuratorFrameworkFactory.newClient(zkServers, retryPolicy);
        client.start();
        this.rootNode = rootNode;
    }


    public CuratorFramework getClient() {
        return client;
    }

    public String getRootNode() {
        return rootNode;
    }
}
