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
import java.util.concurrent.TimeoutException;

/**
 * @author: hong-zhi
 * @date: 2023/5/6 11:18
 * @Description 消费者业务类
 */
@Slf4j
@Service
public class ConsumerService {

    @RabbitListener(queues = "hongzhi-publish-queue", containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(String message,
                               Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            log.info("接收到消息：{}", message);
            Random random = new Random();
            int sleepTime = random.nextInt(10);
            try {
                // 线程休眠 模拟业务处理 休眠时间较短 1s
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                log.info("{}线程中断", Thread.currentThread().getName());
            }
            // 收到确认消息
            if (channel.isOpen()) {
                channel.basicAck(tag, false);
            } else {
                log.info("消费完毕但是Channel关闭了，消息没有确认...");
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
        /**
         * 手动方式关闭Channel 会报错 Channel closed; cannot ack/nack
         * TODO 为什么会报错？
         * SpringBoot AMPQ自动配置CachingConnectionFactory 缓存Channel会在使用完毕后自动返回给缓存池 并且自动关闭
         * 所以不需要手动关闭Channel
         *
         */
//        finally {
//            if (channel != null) {
//                try {
//                    channel.close();
//                } catch (Exception e) {
//                    log.error("channel关闭失败");
//                }
//            }
//        }

    }
}
