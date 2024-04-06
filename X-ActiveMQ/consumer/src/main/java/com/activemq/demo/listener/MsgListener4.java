package com.activemq.demo.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 死信测试
 */
@Component
public class MsgListener4 {


    @JmsListener(destination = "deadQueue1")
    public void listener1(TextMessage msg, Session session) throws JMSException {
        System.out.println(msg.getText());
        System.out.println(msg.getJMSRedelivered() ? "重发": "首次发送");
        session.rollback();
    }

    @JmsListener(destination = "deadQueue2")
    public void listener2(TextMessage msg, Session session) throws JMSException {
        System.out.println(msg.getText());
        int i = 1 / 0;
        session.commit();
    }

    @JmsListener(destination = "deadQueue3", containerFactory = "noTxJmsListenerContainerFactory")
    public void listener3(TextMessage msg, Session session) throws JMSException {
        System.out.println(msg.getText());
        session.recover();
    }


}
