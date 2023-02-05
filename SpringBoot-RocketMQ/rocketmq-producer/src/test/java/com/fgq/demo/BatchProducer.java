package com.fgq.demo;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class BatchProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    void syncSend() {
        List<Message> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(MessageBuilder.withPayload("批量同步消息" + i).build());
        }
        SendResult sendResult = rocketMQTemplate.syncSend("Batch_Topic", list);

        System.out.println(sendResult);
    }

    @Test
    void asyncSend() throws InterruptedException {
        List<Message> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(MessageBuilder.withPayload("批量异步消息" + i).build());
        }
        rocketMQTemplate.asyncSend("Batch_Topic", list, new SendCallback() {
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
        List<Message> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(MessageBuilder.withPayload("批量单向消息" + i).build());
        }
        rocketMQTemplate.sendOneWay("Batch_Topic", list);
    }
}
