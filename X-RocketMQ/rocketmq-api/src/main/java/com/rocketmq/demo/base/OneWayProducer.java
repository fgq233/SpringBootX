package com.rocketmq.demo.base;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送单向消息
 */
public class OneWayProducer {

    public static void main(String[] args) throws Exception, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("FGQ_TEST_GROUP");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        Message msg = new Message("mq_base", "Tag3", "Hello RocketMQ，单向消息".getBytes());
        producer.sendOneway(msg);
        producer.shutdown();
    }
}
