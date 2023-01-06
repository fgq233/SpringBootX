package com.fgq.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CuratorTest {

    @Autowired
    private CuratorFramework client;

    //===========================close================================================================================
    @AfterEach
    void close() {
        if (client != null) {
            client.close();
        }
    }

//===========================create================================================================================


    /**
     * 1. 基本创建
     */
    @Test
    void testCreate() throws Exception {
        //如果创建节点，没有指定数据，则默认将当前客户端的ip作为数据存储
        String path = client.create().forPath("/app1");
        System.out.println(path);
    }

    /**
     * 2. 创建节点 带有数据 data.getBytes()
     */
    @Test
    void testCreate2() throws Exception {
        String path = client.create().forPath("/app2", "fgq666".getBytes());
        System.out.println(path);
    }

    /**
     * 3. 创建节点 设置节点的类型：withMode()
     */
    @Test
    void testCreate3() throws Exception {
        String path = client.create().withMode(CreateMode.EPHEMERAL).forPath("/app3");
        System.out.println(path);
    }

    /**
     * 4. 创建多级节点：creatingParentsIfNeeded():如果父节点不存在，则创建父节点
     */
    @Test
    void testCreate4() throws Exception {
        String path = client.create().creatingParentsIfNeeded().forPath("/app4/p1");
        System.out.println(path);
    }


//===========================GET================================================================================


    /**
     * 1. 查询节点数据
     */
    @Test
    void testGet1() throws Exception {
        byte[] data = client.getData().forPath("/app1");
        System.out.println(new String(data));
    }

    /**
     * 2. 查询子节点
     */
    @Test
    void testGet2() throws Exception {
        List<String> path = client.getChildren().forPath("/");
        System.out.println(path);
    }

    /**
     * 3. 查询节点状态信息
     */
    @Test
    void testGet3() throws Exception {
        Stat status = new Stat();
        client.getData().storingStatIn(status).forPath("/app1");
        System.out.println(status);

    }


//===========================set================================================================================

    /**
     * 1. 基本修改数据：setData().forPath()
     */
    @Test
    void testSet() throws Exception {
        client.setData().forPath("/app1", "fgq888".getBytes());
    }

    /**
     * 2. 根据版本修改: setData().withVersion().forPath()
     * * version 是通过查询出来的，目的就是为了让其他客户端或者线程不干扰我。
     */
    @Test
    void testSetForVersion() throws Exception {
        Stat status = new Stat();
        client.getData().storingStatIn(status).forPath("/app1");
        client.setData().withVersion(status.getVersion()).forPath("/app1", "fgq88888888".getBytes());
    }


//===========================delete================================================================================

    /**
     * 1. 删除单个节点:delete().forPath("/app1");
     */
    @Test
    void testDelete() throws Exception {
        client.delete().forPath("/app1");
    }

    /**
     * 2. 删除带有子节点的节点:delete().deletingChildrenIfNeeded().forPath("/app1");
     */
    @Test
    void testDelete2() throws Exception {
        client.delete().deletingChildrenIfNeeded().forPath("/app4");
    }

    /**
     * 3. 必须成功的删除:为了防止网络抖动，本质就是重试  client.delete().guaranteed().forPath("/app2");
     */
    @Test
    void testDelete3() throws Exception {
        client.delete().guaranteed().forPath("/app2");
    }

    /**
     * 4. 回调：inBackground
     */
    @Test
    void testDelete4() throws Exception {
        //4. 回调
        client.delete().guaranteed().inBackground(new BackgroundCallback() {

            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("我被删除了~");
                System.out.println(event);
            }
        }).forPath("/app1");
    }




}
