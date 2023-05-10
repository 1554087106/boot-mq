package org.whz.consumer.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: hong-zhi
 * @date: 2023/5/6 17:37
 * @Description RabbitMQ配置类
 * 配置绑定了路由键的队列
 */
@Slf4j
@Configuration
public class TopicConfig {
    /**
     * Topic模式交换机绑定的队列
     */
    public static final String TOPIC_QUEUE_NAME = "topic-hongzhi000-queue";
    public static final String TOPIC_QUEUE_NAME_1 = "topic-hongzhi001-queue";
    public static final String TOPIC_QUEUE_NAME_2 = "topic-hongzhi002-queue";
    public static final String TOPIC_QUEUE_NAME_3 = "topic-hongzhi003-queue";
    // hongzhi-topic-exchange  Topic模式交换机名称
    public static final String EXCHANGE_TOPIC_NAME = "hongzhi-topic-exchange";

    @Bean(name = "topic-hongzhi000-queue")
    public Queue queue1() {
        return new Queue(TOPIC_QUEUE_NAME);
    }
    @Bean(name = "topic-hongzhi001-queue")
    public Queue queue2() {
        return new Queue(TOPIC_QUEUE_NAME_1);
    }
    @Bean(name = "topic-hongzhi002-queue")
    public Queue queue3() {
        return new Queue(TOPIC_QUEUE_NAME_2);
    }
    @Bean(name = "topic-hongzhi003-queue")
    public Queue queue4() {
        return new Queue(TOPIC_QUEUE_NAME_3);
    }
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_TOPIC_NAME);
    }

    @Bean(name = "topic000")
    public Binding binding000() {
        return BindingBuilder.bind(queue1()).to(topicExchange()).with("*.orange.*");
    }
    @Bean(name = "topic001")
    public Binding binding001() {
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("*.*.rabbit");
    }
    @Bean(name = "topic002")
    public Binding binding002() {
        return BindingBuilder.bind(queue3()).to(topicExchange()).with("lazy.#");
    }
    @Bean(name = "topic003")
    public Binding binding003() {
        return BindingBuilder.bind(queue4()).to(topicExchange()).with("fast.blue.sky");
    }

}
