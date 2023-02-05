package com.fgq.demo.consumers;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/**
 * SQL过滤消息
 */
@Component
@RocketMQMessageListener(
        topic = "SQL_TOPIC",
        consumerGroup = "FGQ_TEST_SQL",
        selectorType = SelectorType.SQL92,
        selectorExpression = "age > 6")
public class SqlFilterConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println(msg);
    }
}


