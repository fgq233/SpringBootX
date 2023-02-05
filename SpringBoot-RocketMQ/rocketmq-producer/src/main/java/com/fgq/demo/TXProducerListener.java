package com.fgq.demo;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RocketMQTransactionListener
public class TXProducerListener implements RocketMQLocalTransactionListener {

    /**
     * 执行本地事务
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        System.out.println("开始执行本地事务...");
        RocketMQLocalTransactionState result;
        try {
            // ... 模拟业务处理

            // 模拟异常
            // int i = 1 / 0;
            result = RocketMQLocalTransactionState.COMMIT;  // 成功
            System.out.println("本地事务执行成功...");
        } catch (Exception e) {
            result = RocketMQLocalTransactionState.ROLLBACK;
            System.out.println("本地事务执行失败...");
        }
        return result;
    }

    /**
     * 本地事务回查
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        System.out.println("检查本地事务...");
        RocketMQLocalTransactionState result;
        try {
            // 检查本地事务，比如检查 msg 数据是否插入数据库，根据检查结果返回 COMMIT/ROLLBACK
            result = RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            // 异常就回滚
            result = RocketMQLocalTransactionState.ROLLBACK;
        }
        return result;
    }
}
