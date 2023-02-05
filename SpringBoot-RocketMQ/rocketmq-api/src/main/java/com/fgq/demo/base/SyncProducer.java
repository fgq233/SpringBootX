package com.fgq.demo.base;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * 发送同步消息
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        //  1. 创建消息生产者，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("FGQ_TEST_GROUP");
        //  2. 指定 NameServer 地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        //  3. 启动
        producer.start();
        // 4. 创建消息对象，指定主题Topic、Tag、消息体
        Message msg = new Message("mq_base", "Tag1", "Hello RocketMQ，同步消息".getBytes());
        // 5. 发送消息
        SendResult result = producer.send(msg);
        System.out.println("发送结果:" + result);
        // 6.关闭生产者
        producer.shutdown();
    }
}
