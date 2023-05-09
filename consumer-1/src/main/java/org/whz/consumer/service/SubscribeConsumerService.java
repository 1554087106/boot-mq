package org.whz.consumer.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author: hong-zhi
 * @date: 2023/5/6 11:18
 * @Description 订阅交换机 -> EXCHANGE_FANOUT_NAME = "hongzhi-fanout-exchange";
 */
@Slf4j
@Service
public class SubscribeConsumerService {
    private final Queue anonymousQueue;

    public SubscribeConsumerService(Queue anonymousQueue) {
        this.anonymousQueue = anonymousQueue;
    }

    /**
     * PUBLISH_QUEUE
     * 由于在配置中设置了手动确认消息
     * 所以这里如果不手动确认，那么就只能接收到一条消息
     * @param message
     */
    @RabbitListener(queues = "#{anonymousQueue.name}")
    public void receiveMessage(String message,
                               Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag) {

        log.info("订阅消息: {} \r\n 匿名队列名称: {}", message, anonymousQueue.getName());
        // 收到确认消息
        if (channel.isOpen()) {
            try {
                channel.basicAck(tag, false);
            } catch (IOException e) {
                log.error("消息确认失败");
            }
        } else {
            log.info("消费完毕但是Channel关闭了，消息没有确认...");
        }
    }
}
