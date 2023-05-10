package org.whz.producer.conf;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Topic模式交换机配置类
 */
@Configuration
public class TopicConfig {

    public static final String TOPIC_QUEUE_NAME = "topic-hongzhi000-queue";
    public static final String TOPIC_QUEUE_NAME_1 = "topic-hongzhi001-queue";
    public static final String TOPIC_QUEUE_NAME_2 = "topic-hongzhi002-queue";
    public static final String TOPIC_QUEUE_NAME_3 = "topic-hongzhi003-queue";
    // hongzhi-topic-exchange  Topic模式交换机名称
    public static final String EXCHANGE_TOPIC_NAME = "hongzhi-topic-exchange";

    /**
     * 创建队列1
     * @return
     */
    @Bean(name = "topic-hongzhi000-queue")
    public Queue queue1() {
        return new Queue(TOPIC_QUEUE_NAME);
    }
    /**
     * 创建队列2
     * @return
     */
    @Bean(name = "topic-hongzhi001-queue")
    public Queue queue2() {
        return new Queue(TOPIC_QUEUE_NAME_1);
    }

    /**
     * route-hongzhi002-queue
     * @return
     */
    @Bean(name = "topic-hongzhi002-queue")
    public Queue queue3() {
        return new Queue(TOPIC_QUEUE_NAME_2);
    }

    /**
     * route-hongzhi003-queue
     * @return
     */
    @Bean(name = "topic-hongzhi003-queue")
    public Queue queue4() {
        return new Queue(TOPIC_QUEUE_NAME_3);
    }


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_TOPIC_NAME);
    }

    /**
     * 绑定交换机和队列
     * @return
     */
    @Bean(name = "topic000")
    public Binding binding6() {
        return BindingBuilder
                .bind(queue1())
                .to(topicExchange())
                .with("*.orange.*");
    }
    @Bean(name = "topic001")
    public Binding binding7() {
        return BindingBuilder
                .bind(queue2())
                .to(topicExchange())
                .with("*.*.rabbit");
    }

    /**
     * 绑定键 红 蓝
     * 绑定到同一个队列
     * @return
     */
    @Bean(name = "topic002")
    public Binding binding8() {
        return BindingBuilder
                .bind(queue3())
                .to(topicExchange())
                .with("lazy.#");
    }
    @Bean(name = "topic003")
    public Binding binding9() {
        return BindingBuilder
                .bind(queue3())
                .to(topicExchange())
                .with("fast.blue.sky");
    }




}
