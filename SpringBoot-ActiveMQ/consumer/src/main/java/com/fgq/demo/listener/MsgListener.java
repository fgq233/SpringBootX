package com.fgq.demo.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class MsgListener {


    @JmsListener(destination = "test.queueX1")
    public void listener1(String msg) {
        System.out.println(msg);
    }


    /**
     *  JMS 消息头
     */
    @JmsListener(destination = "test.queueX2")
    public void listener2(TextMessage msg) throws JMSException {
        System.out.println(msg.getText());
        System.out.println("JMSDestination:" + msg.getJMSDestination());
        System.out.println("JMSMessageID:" + msg.getJMSMessageID());
        System.out.println("JMSDeliveryMode:" + msg.getJMSDeliveryMode());
        System.out.println("JMSTimestamp:" + msg.getJMSTimestamp());
        System.out.println("JMSExpiration:" + msg.getJMSExpiration());
        System.out.println("JMSPriority:" + msg.getJMSPriority());
        System.out.println("JMSCorrelationID:" + msg.getJMSCorrelationID());
        System.out.println("JMSReplyTo:" + msg.getJMSReplyTo());
        System.out.println("JMSType:" + msg.getJMSType());
        System.out.println("JMSRedelivered:" + msg.getJMSRedelivered());
    }


    /**
     * JMS 消息属性
     */
    @JmsListener(destination = "test.queueX3")
    public void listener3(TextMessage msg) throws JMSException {
        System.out.println(msg.getText());
        System.out.println(msg.getStringProperty("prop1"));
        System.out.println(msg.getIntProperty("prop2"));
    }


    /**
     * 消费者事务测试
     */
    @JmsListener(destination = "txQueueX")
    public void listener3(String msg, Session session) throws JMSException {
        try {
            System.out.println(msg);
            int i = 1 / 0;
            session.commit();
        } catch (Exception e) {
            session.rollback();
        }
    }

}
