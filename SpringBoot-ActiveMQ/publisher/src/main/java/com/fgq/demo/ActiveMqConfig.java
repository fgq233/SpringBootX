package com.fgq.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;

@Configuration
public class ActiveMqConfig {


    /**
     * 注入 JMS 事务管理器
     * @param connectionFactory  ActiveMQ 连接工厂
     */
    @Bean
    public PlatformTransactionManager getPlatformTransactionManager(ConnectionFactory connectionFactory) {
        return new JmsTransactionManager(connectionFactory);
    }


}
