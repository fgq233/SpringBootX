package com.rocketmq.demo.base;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("FGQ_TEST_GROUP");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        Message msg = new Message("mq_base", "Tag2", "Hello RocketMQ，异步消息".getBytes());
        //  发送异步消息
        producer.send(msg, new SendCallback() {
            /**
             * 发送成功回调
             */
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功：" + sendResult);
            }

            /**
             * 发送失败回调
             */
            public void onException(Throwable e) {
                System.out.println("发送异常：" + e);
            }
        });
        // 线程睡眠 5s
        TimeUnit.SECONDS.sleep(5);
        producer.shutdown();
    }
}
