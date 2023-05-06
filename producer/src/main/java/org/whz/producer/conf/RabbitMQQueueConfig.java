package org.whz.producer.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: hong-zhi
 * @date: 2023/5/6 10:39
 * @Description RabbitMQ配置类
 * 队列设置
 */
@Slf4j
@Configuration
@EnableRabbit
public class RabbitMQQueueConfig {
    @Autowired
    private ConnectionFactory connectionFactory;

    public static final String QUEUE_NAME = "hongzhi-queue";
    public static final String QUEUE_NAME_NEW = "new-hongzhi-queue";

    public static final String QUEUE_NAME_NON_PERSIS = "hongzhi-queue-no-persis";
    private final AmqpAdmin amqpAdmin;

    public RabbitMQQueueConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    /**
     * 创建队列1
     * @return
     */
    @Bean(name = "queue1")
    public Queue queue1() {
        // 设置队列持久化
        // Spring AMQP默认情况下消息是持久化的，但是队列也是持久化的，服务器重启会丢失队列，那么消息将无法生存
//        boolean durable = true;
        Queue queue = new Queue(QUEUE_NAME);
//        amqpAdmin.declareQueue(queue);
//        AmqpAdmin amqpAdmin = new RabbitAdmin();
        return queue;
    }
    /**
     * 创建队列2
     * @return
     */
    @Bean
    public Queue queue2() {
        return new Queue(QUEUE_NAME_NEW);
    }

    /**
     * 非持久化队列
     * @return
     */
    @Bean
    public Queue queue3() {
        boolean durable = false;
        return new Queue(QUEUE_NAME_NON_PERSIS, durable);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        /**
         * 设置消费者数量
         */
        factory.setConcurrentConsumers(2);
        /**
         * 设置每个消费者预取10条消息
         */
        factory.setPrefetchCount(1);
        /**
         * 启用手动确认模式
         */
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }



}
