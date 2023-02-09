package com.activemq.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.lang.NonNull;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@SpringBootTest
class PublisherTest {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;


    /**
     * 入门
     */
    @Test
    void test() {
        String queueName = "test.queueX1";
        String msg = "Hello ActiveMQ";
//        jmsTemplate.convertAndSend(queueName, msg);
        jmsMessagingTemplate.convertAndSend(queueName, msg);
    }


    /**
     * 测试消息头
     */
    @Test
    void test2() {
        String queueName = "test.queueX2";
        String msg = "Hello ActiveMQ";

        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            @NonNull
            public Message createMessage(@NonNull Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(msg);
                textMessage.setJMSMessageID("001");
                textMessage.setJMSCorrelationID("666");
                return textMessage;
            }
        });
    }

    /**
     * 测试消息属性
     */
    @Test
    void test3() {
        String queueName = "test.queueX3";
        String msg = "Hello ActiveMQ";

        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            @NonNull
            public Message createMessage(@NonNull Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(msg);
                textMessage.setStringProperty("prop1", "666");
                textMessage.setIntProperty("prop2", 888);
                return textMessage;
            }
        });
    }



}
