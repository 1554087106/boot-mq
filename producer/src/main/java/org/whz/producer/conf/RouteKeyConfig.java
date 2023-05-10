package org.whz.producer.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: hong-zhi
 * @date: 2023/5/9 21:39
 * @Description RabbitMQ配置类
 * 按照路由键进行路由
 */
@Slf4j
@Configuration
public class RouteKeyConfig {

    public static final String ROUTE_QUEUE_NAME = "route-hongzhi000-queue";
    public static final String ROUTE_QUEUE_NAME_1 = "route-hongzhi001-queue";
    public static final String ROUTE_QUEUE_NAME_2 = "route-hongzhi002-queue";
    public static final String ROUTE_QUEUE_NAME_3 = "route-hongzhi003-queue";
    // hongzhi-direct-exchange  Direct模式交换机名称
    public static final String EXCHANGE_DIRECT_NAME = "hongzhi-direct-exchange";

    /**
     * 创建队列1
     * @return
     */
    @Bean(name = "route-hongzhi000-queue")
    public Queue queue1() {
        return new Queue(ROUTE_QUEUE_NAME);
    }
    /**
     * 创建队列2
     * @return
     */
    @Bean(name = "route-hongzhi001-queue")
    public Queue queue2() {
        return new Queue(ROUTE_QUEUE_NAME_1);
    }

    /**
     * route-hongzhi002-queue
     * @return
     */
    @Bean(name = "route-hongzhi002-queue")
    public Queue queue3() {
        return new Queue(ROUTE_QUEUE_NAME_2);
    }

    /**
     * route-hongzhi003-queue
     * @return
     */
    @Bean(name = "route-hongzhi003-queue")
    public Queue queue4() {
        return new Queue(ROUTE_QUEUE_NAME_3);
    }


    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_DIRECT_NAME);
    }

    /**
     * 绑定交换机和队列
     * @return
     */
    @Bean
    public Binding binding2() {
        return BindingBuilder
                .bind(queue1())
                .to(directExchange())
                .with("orange");
    }
    @Bean
    public Binding binding3() {
        return BindingBuilder
                .bind(queue2())
                .to(directExchange())
                .with("black");
    }

    /**
     * 绑定键 红 蓝
     * 绑定到同一个队列
     * @return
     */
    @Bean
    public Binding binding4() {
        return BindingBuilder
                .bind(queue3())
                .to(directExchange())
                .with("red");
    }
    @Bean
    public Binding binding5() {
        return BindingBuilder
                .bind(queue3())
                .to(directExchange())
                .with("blue");
    }




}
