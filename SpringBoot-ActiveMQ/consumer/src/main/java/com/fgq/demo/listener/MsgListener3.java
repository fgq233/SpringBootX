package com.fgq.demo.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * 消息确认机制
 */
@Component
public class MsgListener3 {


    /**
     * 手动确认
     */
    @JmsListener(destination = "ackQueue", containerFactory = "noTxJmsListenerContainerFactory")
    public void listener1(TextMessage msg, Session session) throws JMSException {
        System.out.println(msg.getText());
        session.recover();
        // msg.acknowledge();
    }


}
