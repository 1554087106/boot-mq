package org.whz.producer.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author: hong-zhi
 * @date: 2023/5/6 10:39
 * @Description RabbitMQ配置类
 * 队列设置
 */
@Slf4j
@Configuration
public class RabbitMQQueueConfig {
    @Autowired
    private ConnectionFactory connectionFactory;

    public static final String QUEUE_NAME = "hongzhi-queue";
    public static final String QUEUE_NAME_NEW = "new-hongzhi-queue";

    public static final String QUEUE_NAME_NON_PERSIS = "hongzhi-queue-no-persis";
    public static final String EXCHANGE_FANOUT_NAME = "hongzhi-fanout-exchange";
    // hongzhi-direct-exchange  Direct模式交换机名称
    public static final String EXCHANGE_DIRECT_NAME = "hongzhi-direct-exchange";

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

    /**
     * 生成一个名称随机 非持久化 非持久化 独占 自动删除的队列
     * @return
     */
    @Bean
    public Queue anonymousQueue1() {
        return new AnonymousQueue();
    }
    @Bean
    public Queue anonymousQueue2() {
        return new AnonymousQueue();
    }

    /**
     * 交换机的fanout模式
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT_NAME);
    }

    @Bean
    @Primary
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

    @Bean
    /**
     * 绑定交换机和队列
     * @return
     */
    public Binding binding() {
        return BindingBuilder.bind(anonymousQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(anonymousQueue2()).to(fanoutExchange());
    }





}
