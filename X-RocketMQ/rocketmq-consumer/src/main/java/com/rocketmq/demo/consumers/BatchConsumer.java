package com.rocketmq.demo.consumers;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 批量消息
 */
@Component
@RocketMQMessageListener(
        topic = "Batch_Topic",
        consumerGroup = "TEST_GROUP_BATCH")
public class BatchConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println(msg);
    }
}


