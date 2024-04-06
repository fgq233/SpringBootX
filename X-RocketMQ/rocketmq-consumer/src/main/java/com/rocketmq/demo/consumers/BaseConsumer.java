package com.rocketmq.demo.consumers;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/**
 * 入门
 */
@Component
@RocketMQMessageListener(
        topic = "mq_base",
        consumerGroup = "FGQ_TEST_GROUP",
        selectorExpression = "Tag1 || Tag2 || Tag3")
public class BaseConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println(msg);
    }
}


