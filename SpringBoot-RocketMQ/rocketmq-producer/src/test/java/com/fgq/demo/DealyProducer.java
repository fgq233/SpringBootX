package com.fgq.demo;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class DealyProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @Test
    void syncSend() {
        Message message = MessageBuilder.withPayload("同步延迟消息".getBytes()).build();
        rocketMQTemplate.syncSend("Dealy_Topic", message, 8000, 3);
    }

    @Test
    void asyncSend() throws InterruptedException {
        Message message = MessageBuilder.withPayload("异步延迟消息".getBytes()).build();
        rocketMQTemplate.asyncSend("Dealy_Topic", message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功：" + sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送异常：" + throwable.getMessage());
            }
        }, 8000, 2);
        TimeUnit.SECONDS.sleep(100);
    }

}
