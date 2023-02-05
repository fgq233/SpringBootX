package com.fgq.demo.consumers;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * TAG 过滤消息
 */
@Component
@RocketMQMessageListener(
        topic = "TAG_TOPIC",
        consumerGroup = "FGQ_TEST_TAG",
        selectorExpression = "Tag1 || Tag2")
public class TagFilterConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println(msg);
    }
}


