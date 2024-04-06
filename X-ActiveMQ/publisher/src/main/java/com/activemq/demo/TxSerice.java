package com.activemq.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TxSerice {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     *  对消息发送加入事务管理，同时也对数据库的事务生效
     */
    @Transactional
    public void test() {
        for (int i = 0; i < 10; i++) {
            if (i == 8) {
                int x = 1 / 0;
            }
            jmsMessagingTemplate.convertAndSend("txQueueX", "Hello ActiveMQ");
        }
    }

}
