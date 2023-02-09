package com.rocketmq.demo;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * 入门
 */
@SpringBootTest
class BaseProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    void convertAndSend() {
        rocketMQTemplate.convertAndSend("mq_base:Tag1", "Hello RocketMQ，普通消息");
    }

    @Test
    void syncSend() {
        SendResult sendResult = rocketMQTemplate.syncSend("mq_base:Tag1", "Hello RocketMQ，同步消息");
        System.out.println(sendResult);
    }

    @Test
    void asyncSend() throws InterruptedException {
        rocketMQTemplate.asyncSend("mq_base:Tag2", "Hello RocketMQ，异步消息", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功：" + sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送异常：" + throwable.getMessage());
            }
        });
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    void sendOneWay() {
        rocketMQTemplate.sendOneWay("mq_base:Tag3", "Hello RocketMQ，单向消息");
    }


}
