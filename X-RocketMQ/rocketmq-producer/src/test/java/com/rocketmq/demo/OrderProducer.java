package com.rocketmq.demo;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class OrderProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    private List<Order> list;

    @BeforeEach
    void before() {
        list = Arrays.asList(
                new Order(1, "巴黎世家1"),
                new Order(1, "巴黎世家2"),
                new Order(1, "巴黎世家3"),
                new Order(2, "华伦天奴1"),
                new Order(2, "华伦天奴2"),
                new Order(2, "华伦天奴3"),
                new Order(3, "维多利亚的秘密1"),
                new Order(3, "维多利亚的秘密2"),
                new Order(3, "维多利亚的秘密3")
        );
        rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                String orderId = (String) o;
                int index = Integer.parseInt(orderId) % list.size();
                return list.get(index);
            }
        });
    }

    /**
     * 有序消息
     */
    @Test
    void syncSendOrderly() {
        for (Order order : list) {
            SendResult result = rocketMQTemplate.syncSendOrderly("Order_Topic", order.getDesc(), String.valueOf(order.getOrderId()));
            System.out.println("发送结果：" + result.getMessageQueue());
        }
    }


}
