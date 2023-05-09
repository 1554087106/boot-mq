package org.whz.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.whz.producer.conf.RabbitMQQueueConfig.EXCHANGE_FANOUT_NAME;

/**
 * @author: hong-zhi
 * @date: 2023/5/8 14:48
 * @Description 消息发布者
 */
@Slf4j
@Service
public class MessagePublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(String message) {
        log.info("向交换机发布消息");
        /**
         * 传三个参数: String exchange, String routingKey, final Object object
         * exchange: 交换机名称
         * routingKey: 路由键
         * object: 消息体
         */
        rabbitTemplate.convertAndSend(EXCHANGE_FANOUT_NAME, "", "向交换机发布 -> " + message);
    }
}
