package org.whz.consumer.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        // 设置消费者数量
        factory.setConcurrentConsumers(1);
        // 预先处理消息的数量
        factory.setPrefetchCount(1);
        return factory;

    }

    /**
     * 和发布者的Fanout的名称保持一致
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT_NAME);
    }

    /**
     * 定义一个匿名队列
     *  存在问题:只能接收一次消息，因为队列在消费者断开连接后会自动删除
     * @return
     */
    @Bean
    public Queue anonymousQueue1() {
        return new AnonymousQueue();
    }

    /**
     * 用于接收发布者消息的具名队列
     * @return
     */
    @Qualifier("hongzhi-publish-queue")
    @Bean
    public Queue queue() {
        return new Queue(PUBLISH_QUEUE);
    }

    /**
     * 将具名队列与交换机绑定
     * @return
     */
    @Bean
    public Binding anonymousQueueBinding() {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }
}
