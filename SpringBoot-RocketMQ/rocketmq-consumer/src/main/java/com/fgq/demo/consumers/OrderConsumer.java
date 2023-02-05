package com.fgq.demo.consumers;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 有序消息
 */
@Component
@RocketMQMessageListener(
        topic = "Order_Topic",
        consumerGroup = "TEST_GROUP_ORDERLY",
        consumeMode = ConsumeMode.ORDERLY)
public class OrderConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println(Thread.currentThread().getName() + ":" + msg);
    }
}


