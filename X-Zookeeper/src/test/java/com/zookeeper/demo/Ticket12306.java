package com.zookeeper.demo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Ticket12306 implements Runnable {


    private int tickets = 10;//数据库的票数

    private InterProcessMutex lock;


    public Ticket12306() {
        //重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 10);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(60 * 1000)     // 会话超时60s
                .connectionTimeoutMs(15 * 1000)  // 连接超时30s
                .retryPolicy(retryPolicy)
                //.namespace("fgq")               // 命名空间，生成的节点会自动在前面带上
                .build();
        client.start();

        lock = new InterProcessMutex(client, "/lock");
    }

    @Override
    public void run() {
        while (true) {
            //获取锁
            try {
                lock.acquire();
                if (tickets > 0) {

                    System.out.println(Thread.currentThread() + ":" + tickets);
                    Thread.sleep(100);
                    tickets--;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //释放锁
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

    }
}
