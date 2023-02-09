package com.activemq.demo.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@Configuration
public class ActiveMqConfig {


    /**
     * 关闭事务的JMS监听工厂
     */
    @Bean(name = "noTxJmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 关闭事务
        factory.setSessionTransacted(false);
        //  消息确认机制：手动单条确认 4
        factory.setSessionAcknowledgeMode(ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
        return factory;
    }


    /**
     * 消息重发策略
     */
    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //  是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //  重发次数，默认为6次
        redeliveryPolicy.setMaximumRedeliveries(3);
        //  重发时间间隔，默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(3000);
        //  第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //  是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //  设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        return redeliveryPolicy;
    }


    /**
     * 将重发策略注入到 ActiveMQ 连接工厂
     */
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(@Value("${spring.activemq.broker-url}") String url, RedeliveryPolicy redeliveryPolicy) {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("admin", "admin", url);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return activeMQConnectionFactory;
    }


}
