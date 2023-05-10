package org.whz.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;
/**
 * @author: hong-zhi
 * @date: 2023/5/10 14:48
 * @Description Topic模式交换机发送消息
 */
@Slf4j
@Service
public class MessageTopic {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TopicExchange topicExchange;
    private static final String[] keys = {"quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox",
                                            "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"};

    /**
     * Topic模式生产者发布消息
     * @param message
     */
    public void send(String message) {
        log.info("Topic模式发送消息");
        // 0~6 随机数
        int keyIndex = new Random().nextInt(6);
        String routeKey = keys[keyIndex];
        log.info("\r\n TOPIC模式路由键值: {}", keys[keyIndex]);
        /**
         * 传三个参数: String exchange, String routingKey, final Object object
         * exchange: 交换机名称
         * routingKey: 路由键
         * object: 消息体
         */
        rabbitTemplate.convertAndSend(topicExchange.getName(), routeKey, "向交换机发布消息 -> " + message);
    }
}
