package com.zookeeper.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CuratorWatcherTest {


    @Autowired
    private CuratorFramework client;

    //===========================close================================================================================
    @AfterEach
    void close() {
        if (client != null) {
            client.close();
        }
    }


    /**
     * 演示 NodeCache：给指定一个节点注册监听器
     */
    @Test
    void testNodeCache() throws Exception {
        //1. 创建NodeCache对象
        final NodeCache nodeCache = new NodeCache(client, "/app1");
        //2. 注册监听
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("节点变化了~");
                //获取修改节点后的数据
                byte[] data = nodeCache.getCurrentData().getData();
                System.out.println(new String(data));
            }
        });

        //3. 开启监听.如果设置为true，则开启监听是，加载缓冲数据
        nodeCache.start(true);

        while (true) {

        }
    }


    /**
     * 演示 PathChildrenCache：监听某个节点的所有子节点们
     */

    @Test
    void testPathChildrenCache() throws Exception {
        //1.创建监听对象
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/app2", true);

        //2. 注册监听
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                System.out.println("子节点变化了~");
                System.out.println(event);
                // 根据 type 判断事件类型
                PathChildrenCacheEvent.Type type = event.getType();
                if (type.equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
                    System.out.println("新增了节点！！！");
                } else if (type.equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                    System.out.println("节点数据更新了！！！");
                    byte[] data = event.getData().getData();
                    System.out.println(new String(data));
                } else if (type.equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                    System.out.println("删除了节点！！！");
                }
            }
        });
        //3. 开启
        pathChildrenCache.start();

        while (true) {

        }
    }


    /**
     * 演示 TreeCache：监听某个节点自己和所有子节点们
     */

    @Test
    void testTreeCache() throws Exception {
        //1. 创建监听器
        TreeCache treeCache = new TreeCache(client, "/app2");

        //2. 注册监听
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                System.out.println("节点变化了");
                System.out.println(event);
                // 根据 type 判断事件类型
                TreeCacheEvent.Type type = event.getType();
                if (type.equals(TreeCacheEvent.Type.NODE_ADDED)) {
                    System.out.println("新增了节点！！！");
                } else if (type.equals(TreeCacheEvent.Type.NODE_UPDATED)) {
                    System.out.println("节点数据更新了！！！");
                    byte[] data = event.getData().getData();
                    System.out.println(new String(data));
                } else if (type.equals(TreeCacheEvent.Type.NODE_REMOVED)) {
                    System.out.println("删除了节点！！！");
                }
            }
        });

        //3. 开启
        treeCache.start();

        while (true) {

        }
    }

}
