package org.whz.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

import static org.whz.producer.conf.RabbitMQQueueConfig.EXCHANGE_FANOUT_NAME;

/**
 * @author: hong-zhi
 * @date: 2023/5/8 14:48
 * @Description 按照路由键发送消息
 */
@Slf4j
@Service
public class MessageRoute {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange directExchange;
    private final String[] routeKeyArr = {"orange", "black", "red", "blue"};

    private AtomicLong count = new AtomicLong(0);
    public void send(String message) {
        log.info("按照路由键发送消息");
        String routeKey = routeKeyArr[(int) (count.getAndIncrement() % routeKeyArr.length)];
        log.info("按照路由键: {} 发送消息", routeKey);
        /**
         * 传三个参数: String exchange, String routingKey, final Object object
         * exchange: 交换机名称
         * routingKey: 路由键
         * object: 消息体
         */
        rabbitTemplate.convertAndSend(directExchange.getName(), routeKey, "向交换机发布消息 -> " + message);
    }
}
