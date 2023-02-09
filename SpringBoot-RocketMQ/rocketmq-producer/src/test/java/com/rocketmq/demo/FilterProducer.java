package com.rocketmq.demo;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FilterProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    /**
     * TAG 过滤
     */
    @Test
    void tag() {
        rocketMQTemplate.syncSend("TAG_TOPIC", "无TAG");
        rocketMQTemplate.syncSend("TAG_TOPIC:Tag1", "有TAG1");
        rocketMQTemplate.syncSend("TAG_TOPIC:Tag2", "有TAG2");
        rocketMQTemplate.syncSend("TAG_TOPIC:Tag3", "有TAG3");
    }


    /**
     * SQL 过滤
     */
    @Test
    void sql() {
        List<Message> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(MessageBuilder
                    .withPayload("SQ过滤消息" + i)
                    .setHeader("age", i)
                    .build());
        }
        SendResult result = rocketMQTemplate.syncSend("SQL_TOPIC", list);
        System.out.println(result);
    }


}
