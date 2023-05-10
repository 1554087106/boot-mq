package org.whz.consumer.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
public class RouteKeyConfig {
    /**
     * 直连交换机绑定的队列
     */
    public static final String ROUTE_QUEUE_NAME = "route-hongzhi000-queue";
    public static final String ROUTE_QUEUE_NAME_1 = "route-hongzhi001-queue";
    public static final String ROUTE_QUEUE_NAME_2 = "route-hongzhi002-queue";
    public static final String ROUTE_QUEUE_NAME_3 = "route-hongzhi003-queue";
    // hongzhi-direct-exchange  Direct模式交换机名称
    public static final String EXCHANGE_DIRECT_NAME = "hongzhi-direct-exchange";

    @Bean(name = "route-hongzhi000-queue")
    public Queue queue1() {
        return new Queue(ROUTE_QUEUE_NAME);
    }
    @Bean(name = "route-hongzhi001-queue")
    public Queue queue2() {
        return new Queue(ROUTE_QUEUE_NAME_1);
    }
    @Bean(name = "route-hongzhi002-queue")
    public Queue queue3() {
        return new Queue(ROUTE_QUEUE_NAME_2);
    }
    @Bean(name = "route-hongzhi003-queue")
    public Queue queue4() {
        return new Queue(ROUTE_QUEUE_NAME_3);
    }
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_DIRECT_NAME);
    }

    @Bean
    public Binding binding000() {
        return BindingBuilder.bind(queue1()).to(directExchange()).with("orange");
    }
    @Bean
    public Binding binding001() {
        return BindingBuilder.bind(queue2()).to(directExchange()).with("black");
    }
    @Bean
    public Binding binding002() {
        return BindingBuilder.bind(queue3()).to(directExchange()).with("red");
    }
    @Bean
    public Binding binding003() {
        return BindingBuilder.bind(queue4()).to(directExchange()).with("blue");
    }

}
