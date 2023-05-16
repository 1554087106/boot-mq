package org.whz.producer.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.whz.producer.service.MessageRPC;
import org.whz.producer.service.MessageTopic;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: hong-zhi
 * @date: 2023/5/6 15:45
 * @Description 每5秒钟发布一条消息
 * 调用RPC服务(RabbitMQ模拟)
 */
@Slf4j
@Component
public class RPCMessage {
    /**
     * 线程安全的计数器
     */
    AtomicInteger count = new AtomicInteger(0);
    @Autowired
    private MessageRPC messageTopic;

    @Scheduled(fixedRate = 1000)
    public void sendMessage() {
         log.info("\r\n调用RPC{}：{}", count.get(), LocalDateTime.now());
         messageTopic.send();
         count.addAndGet(1);
    }
}
