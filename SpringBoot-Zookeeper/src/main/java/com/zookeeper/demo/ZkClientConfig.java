package com.zookeeper.demo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZkClientConfig {


    @Bean
    public CuratorFramework createWithOptions(){
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
        return client;
    }

}
