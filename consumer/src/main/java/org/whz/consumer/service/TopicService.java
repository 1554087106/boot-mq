package org.whz.consumer.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import java.io.IOException;

/**
 * @author: hong-zhi
 * @date: 2023/5/10 11:18
 * @Description Topic模式交换机接收消息
 */
@Slf4j
@Service
public class TopicService {


    /**
     * 配置队列名称
     *  交换机和队列的绑定信息已经在配置类中声明
     *  匹配模式 *.orange.*
     * @param message
     * @param channel 自动传入
     * @param tag 自动传入
     * @param routingKey 自动传入
     */
    @RabbitListener(queues = "topic-hongzhi000-queue")
    public void receiveMessage(String message,
                               Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                               @Header("amqp_receivedRoutingKey") String routingKey) {

        log.info("\r\n订阅消息: {} \r\n "  +"路由键名称: {}",
                message,routingKey);

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
     * 配置队列名称
     *  交换机和队列的绑定信息已经在配置类中声明
     *  匹配模式 *.*.rabbit
     * @param message
     * @param channel 自动传入
     * @param tag 自动传入
     * @param routingKey 自动传入
     */
    @RabbitListener(queues = "topic-hongzhi001-queue")
    public void receiveMessage1(String message,
                               Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                               @Header("amqp_receivedRoutingKey") String routingKey) {

        log.info("\r\n订阅消息: {} \r\n "  +"路由键名称: {}", message,routingKey);

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
     * 配置队列名称
     *  交换机和队列的绑定信息已经在配置类中声明
     *  匹配模式 lazy.#
     * @param message
     * @param channel 自动传入
     * @param tag 自动传入
     * @param routingKey 自动传入
     */
    @RabbitListener(queues = "topic-hongzhi002-queue")
    public void receiveMessage2(String message,
                               Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                               @Header("amqp_receivedRoutingKey") String routingKey) {

        log.info("\r\n订阅消息: {} \r\n " + "路由键名称: {}",
                message,routingKey);

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
