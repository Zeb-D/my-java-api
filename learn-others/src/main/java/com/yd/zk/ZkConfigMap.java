package com.yd.zk;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.ZKPaths;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zeb灬D on 2017/12/22.
 */
public class ZkConfigMap extends ConcurrentHashMap<String, String> {
    private ZkConfigProfile zkConfigProfile;
    private String node;

    private CuratorFramework client;

    private List<IObserver> observerList = Lists.newArrayList();

    public ZkConfigMap(ZkConfigProfile zkConfigProfile, String node) {
        this.zkConfigProfile = zkConfigProfile;
        this.node = node;
        this.client = zkConfigProfile.getClient();
        init();
    }

    private void init() {
        loadAllNode();
    }

    private void loadAllNode() {
        String mapPath = ZKPaths.makePath(zkConfigProfile.getRootNode(), node);

        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, mapPath, true);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                ChildData data = event.getData();
                if (data != null) {
                    switch (event.getType()) {
                        case INITIALIZED:
                            loadNode(data.getPath(), new String(data.getData()));
                            break;
                        case CHILD_ADDED:
                            loadNode(data.getPath(), new String(data.getData()));
                            break;
                        case CHILD_REMOVED:
                            removeNode(data.getPath(), new String(data.getData()));
                            break;
                        case CHILD_UPDATED:
                            reloadNode(data.getPath(), new String(data.getData()));
                            break;
                    }
                } else {
                    System.out.println("data is null : " + event.getType());
                }
            }
        });
        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reloadNode(String path, String value) {
        String key = ZKPaths.getNodeFromPath(path);
        this.put(key, value);
        notify(key, value);
    }

    private void removeNode(String path, String value) {
        String deletedKey = ZKPaths.getNodeFromPath(path);
        this.remove(deletedKey);
        notify(deletedKey, null);
    }

    private void loadNode(String path, String value) {
        String createdKey = ZKPaths.getNodeFromPath(path);
        this.put(createdKey, value);
        notify(createdKey, value);
    }

    public void notify(String key, String value) {
        observerList.stream().forEach(oberver -> oberver.notified(key, value));
    }

    public void registerListener(IObserver observer) {
        this.observerList.add(observer);
    }
}
