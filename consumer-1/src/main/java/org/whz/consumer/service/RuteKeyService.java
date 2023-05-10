package org.whz.consumer.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author: hong-zhi
 * @date: 2023/5/6 11:18
 * @Description 根据路由键进行消息接收
 */
@Slf4j
@Service
public class RuteKeyService {


    /**
     * 接收路由键为 orange 的消息
     * @param message
     * @param channel
     * @param tag
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "route-hongzhi000-queue", durable = "true"),
            exchange = @Exchange(value = "hongzhi-direct-exchange", type = ExchangeTypes.DIRECT),
            key = "orange"
    ))
    public void receiveMessage(String message,
                               Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                               @Header("amqp_receivedRoutingKey") String routingKey,
                               org.springframework.amqp.core.Queue queue) {

        log.info("\r\n订阅消息: {} \r\n " +
                "具名队列名称: {}\r\n " +
                "路由键名称: {}",
                message,
                queue.getName(),
                routingKey);

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

    /**
     * 接收路由键为 blue 的消息
     * @param message
     * @param channel
     * @param tag
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "route-hongzhi003-queue", durable = "true"),
            exchange = @Exchange(value = "hongzhi-direct-exchange", type = ExchangeTypes.DIRECT),
            key = "blue"
    ))
    public void receiveMessage3(String message,
                               Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                               @Header("amqp_receivedRoutingKey") String routingKey,
                               org.springframework.amqp.core.Queue queue) {

        log.info("\r\n订阅消息: {} \r\n " +
                        "具名队列名称: {}\r\n " +
                        "路由键名称: {}",
                message,
                queue.getName(),
                routingKey);

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

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "route-hongzhi002-queue", durable = "true"),
            exchange = @Exchange(value = "hongzhi-direct-exchange", type = ExchangeTypes.DIRECT),
            key = "red"
    ))
    public void receiveMessage4(String message,
                                Channel channel,
                                @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                                @Header("amqp_receivedRoutingKey") String routingKey,
                                org.springframework.amqp.core.Queue queue) {

        log.info("\r\n订阅消息: {} \r\n " +
                        "具名队列名称: {}\r\n " +
                        "路由键名称: {}",
                message,
                queue.getName(),
                routingKey);

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
