package org.whz.consumer.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: hong-zhi
 * @date: 2023/5/6 11:18
 * @Description 消费者业务类
 */
@Slf4j
@Service
public class ConsumerService {

    /**
     * concurrency = "1" 表示只有一个消费者处理消息
     * @param message
     * @param channel
     * @param tag
     */
    @RabbitListener(queues = "hongzhi-queue", containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(String message,
                               Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            log.info("接收到消息：{}", message);
            Random random = new Random();
            int sleepTime = random.nextInt(10);
            try {
                // 线程休眠 模拟业务处理 休眠时间较长 20s
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                log.info("{}线程中断", Thread.currentThread().getName());
            }
            if (channel.isOpen()) {
                // 收到确认消息
                channel.basicAck(tag, false);
            } else {
                log.info("消费完成但是Channel关闭了，消息没有确认...");
            }


        } catch (IOException e) {
            // 确认消息失败
            // 拒绝消息并重新发送到队列
            try {
                if (channel.isOpen()) {

                    channel.basicNack(tag, false, true);
                } else {
                    log.info("Channel关闭了，但是消息没有确认...");
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
