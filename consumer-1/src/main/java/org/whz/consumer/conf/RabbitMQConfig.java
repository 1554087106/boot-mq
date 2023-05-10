package org.whz.consumer.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: hong-zhi
 * @date: 2023/5/6 17:37
 * @Description 为每个消费者配置一个唯一的消费者标签
 */
@Slf4j
@Configuration
public class RabbitMQConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;
    public static final String EXCHANGE_FANOUT_NAME = "hongzhi-fanout-exchange";

    public static final String PUBLISH_QUEUE = "hongzhi-publish-queue";

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setConcurrentConsumers(1);
        // 预先处理消息的数量
        factory.setPrefetchCount(1);
        return factory;
    }
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT_NAME);
    }

    @Bean
    public Queue anonymousQueue() {
        return new AnonymousQueue();
    }

    /**
     * 与发布者的交换机名称保持一致
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(anonymousQueue()).to(fanoutExchange());
    }
}
