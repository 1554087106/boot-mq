package org.whz.producer.service;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.whz.producer.conf.RabbitMQQueueConfig;

/**
 * @author: hong-zhi
 * @date: 2023/5/6 10:47
 * @Description 生产者业务类
 */
@Service
public class ProducerService {
    /**
     * RabbitTemplate默认将消息发送到默认交换机
     * 每个队列都会自动绑定到名称为队列名的绑定键的默认交换机上
     * 这就是为什么我们可以使用队列名作为路由键来确保消息最终进入队列中
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitMQQueueConfig.QUEUE_NAME, message, m -> {
            // 设置消息持久化 防止消息丢失
            m.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return m;
        });
    }
    public void sendMessage2(String message) {
        rabbitTemplate.convertAndSend(RabbitMQQueueConfig.QUEUE_NAME_NEW, message);
    }

    public void sendMessage3(String message) {
        rabbitTemplate.convertAndSend(RabbitMQQueueConfig.QUEUE_NAME_NON_PERSIS, message, p -> {
            // 设置消息非持久化
            p.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
            return p;
        });
    }
}
