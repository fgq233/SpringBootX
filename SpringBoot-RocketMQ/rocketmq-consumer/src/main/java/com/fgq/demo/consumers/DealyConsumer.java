package com.fgq.demo.consumers;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 延迟消息
 */
@Component
@RocketMQMessageListener(
        topic = "Dealy_Topic",
        consumerGroup = "TEST_GROUP_DEALY")
public class DealyConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println(msg);
    }
}


