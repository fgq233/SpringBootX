package com.activemq.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;

/**
 * 死信测试
 */
@SpringBootTest
class DeadTest {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;


    @Test
    void test() {
        jmsMessagingTemplate.convertAndSend("deadQueue1", "session.rollback()");
//        jmsMessagingTemplate.convertAndSend("deadQueue2", "session not commit() ");
//        jmsMessagingTemplate.convertAndSend("deadQueue3", "session.recover()");
    }

}
