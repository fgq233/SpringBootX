package com.rocketmq.demo.consumers;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 事务消息
 */
@Component
@RocketMQMessageListener(
        topic = "TRANSACTION_TOPIC",
        consumerGroup = "FGQ_TEST_TRANSACTION")
public class TransactionConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println(msg);
    }

}
