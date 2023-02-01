package com.fgq.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;

@SpringBootTest
class AckTest {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;


    /**
     * 手动确认模式测试
     */
    @Test
    void test() {
        jmsMessagingTemplate.convertAndSend("ackQueue", "AckTest");
    }


}
