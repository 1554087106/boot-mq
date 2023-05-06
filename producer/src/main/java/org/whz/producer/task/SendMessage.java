package org.whz.producer.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.whz.producer.service.ProducerService;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: hong-zhi
 * @date: 2023/5/6 15:45
 * @Description 每5秒钟发送一条消息
 */
@Slf4j
@Component
public class SendMessage {
    /**
     * 线程安全的计数器
     */
    AtomicInteger count = new AtomicInteger(0);
    @Autowired
    private ProducerService producerService;

    @Scheduled(fixedRate = 1000)
    public void sendMessage() {
         log.info("发送消息{}：{}", count.get(), LocalDateTime.now());
         producerService.sendMessage("我是一条消息" + count.get());
         count.addAndGet(1);
    }
}
