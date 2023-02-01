package com.fgq.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.lang.NonNull;

import javax.jms.*;

@SpringBootTest
class TypeTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 5种消息格式
     */
    @Test
    void test() {
        jmsTemplate.send("test.queue1", new MessageCreator() {
            @Override
            @NonNull
            public Message createMessage(@NonNull Session session) throws JMSException {
                return session.createTextMessage("Hello ActiveMQ");
            }
        });
        jmsTemplate.send("test.queue2", new MessageCreator() {
            @Override
            @NonNull
            public Message createMessage(@NonNull Session session) throws JMSException {
                MapMessage map = session.createMapMessage();
                map.setString("name", "fgq");
                map.setInt("age", 18);
                return map;
            }
        });
        jmsTemplate.send("test.queue3", new MessageCreator() {
            @Override
            @NonNull
            public Message createMessage(@NonNull Session session) throws JMSException {
                SysUser user = new SysUser();
                user.setName("fgq");
                user.setAge(88);
                return session.createObjectMessage(user);
            }
        });
        jmsTemplate.send("test.queue4", new MessageCreator() {
            @Override
            @NonNull
            public Message createMessage(@NonNull Session session) throws JMSException {
                BytesMessage bytesMessage = session.createBytesMessage();
                bytesMessage.writeBytes("fgq666".getBytes());
                return bytesMessage;
            }
        });
        jmsTemplate.send("test.queue5", new MessageCreator() {
            @Override
            @NonNull
            public Message createMessage(@NonNull Session session) throws JMSException {
                StreamMessage streamMessage = session.createStreamMessage();
                streamMessage.writeString("你好啊，范老师");
                streamMessage.writeInt(666);
                return streamMessage;
            }
        });
    }


}
