package com.fgq.demo.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * 测试 5种 格式 消息数据
 */
@Component
public class MsgListener2 {


    @JmsListener(destination = "test.queue1")
    public void listener1(TextMessage msg) throws JMSException {
        System.out.println("TextMessage:" + msg.getText());
    }

    @JmsListener(destination = "test.queue2")
    public void listener2(MapMessage msg) throws JMSException {
        System.out.println("MapMessage:" + msg.getString("name"));
        System.out.println("MapMessage:" + msg.getInt("age"));
    }

    @JmsListener(destination = "test.queue3")
    public void listener3(ObjectMessage msg) throws JMSException {
        System.out.println("ObjectMessage:" + msg);
    }

    @JmsListener(destination = "test.queue4")
    public void listener4(BytesMessage msg) throws JMSException {
        long length = msg.getBodyLength();
        byte[] bytes = new byte[(int) length];
        msg.readBytes(bytes);
        System.out.println("BytesMessage:" + bytes);
    }

    @JmsListener(destination = "test.queue5")
    public void listener5(StreamMessage msg) throws JMSException {
        System.out.println("StreamMessage:" + msg.readString());
        System.out.println("StreamMessage:" + msg.readInt());
    }
}
