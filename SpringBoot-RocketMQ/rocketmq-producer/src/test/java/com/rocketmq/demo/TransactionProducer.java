package com.rocketmq.demo;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 事务消息
 */
@SpringBootTest
class TransactionProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @Test
    void tx() {
        Message<String> message = MessageBuilder.withPayload("事务消息").build();
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction("TRANSACTION_TOPIC", message, null);

        LocalTransactionState transactionState = result.getLocalTransactionState();
        System.out.println(result);
        // 事务状态
        System.out.println(transactionState);
    }



}
