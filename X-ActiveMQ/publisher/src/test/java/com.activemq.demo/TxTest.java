package com.activemq.demo;

import com.activemq.demo.TxSerice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;

@SpringBootTest
class TxTest {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private TxSerice txSerice;

    /**
     * 生产者：未开启事务
     */
    @Test
    void test() {
        for (int i = 0; i < 10; i++) {
            if (i == 8) {
                int x = 1 / 0;
            }
            jmsMessagingTemplate.convertAndSend("txQueueX", "Hello ActiveMQ");
        }

    }


    /**
     * 生产者：开启事务
     */
    @Test
    void test2() {
        txSerice.test();
    }


    /**
     * 消费者事务测试
     */
    @Test
    void test3() {
        jmsMessagingTemplate.convertAndSend("txQueueX", "Hello ActiveMQ");
    }

}
