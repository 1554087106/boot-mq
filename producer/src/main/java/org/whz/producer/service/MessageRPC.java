package org.whz.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static org.whz.producer.conf.RPCConfig.ROUTE_KEY_RPC;

/**
 * @author: hong-zhi
 * @date: 2023/5/10 14:48
 * @Description 模拟RPC服务
 */
@Slf4j
@Service
public class MessageRPC {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    @Qualifier("hongzhi-rpc-exchange")
    private DirectExchange directExchange;
    private AtomicLong start = new AtomicLong(1);

    /**
     * 模拟RPC调用消费者端的斐波那契数列结果
     * 实现在消费者端完成计算任务
     */
    public void send() {
        log.info("模拟RPC发送消息,请求参数: {}", start.get());
        // UUID生成请求ID
        String requestID = UUID.randomUUID().toString();
        MessageProperties props = new MessageProperties();
        props.setCorrelationId(requestID);
        props.setReplyTo(ROUTE_KEY_RPC);
        // long转byte[]
//        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
//        buffer.putLong(start.get());
//        byte[] messageBodyBytes = buffer.array();
        // Message类的目的是简单地封装主体和属性在一个实例中，以便于AMQP API其余部分变得更简单。
//        Message requestMessage = new Message(bytes, props);
        // 将发布者确认与已发送的消息进行关联的基类
        // 自动产生随机值
        CorrelationData correlationData = new CorrelationData();
        MessagePostProcessor messagePostProcessor = message -> {
            String correlationId = correlationData.getId();
            String replyTo = ROUTE_KEY_RPC;
            message.getMessageProperties().setCorrelationId(correlationData.getId());
            message.getMessageProperties().setReplyTo(ROUTE_KEY_RPC);
            log.info("correlationId: {}\r\nreplyTo: {}", correlationId, replyTo);
            return message;
        };


        /**
         * Object convertSendAndReceive(String exchange, String routingKey, Object message, CorrelationData correlationData)
         * 转换，发送一条消息并返回回复信息
         *
         */

        Object response = rabbitTemplate.convertSendAndReceive(directExchange.getName(), ROUTE_KEY_RPC, start.get(), messagePostProcessor);
        log.info("RPC回复的数据: {}", response);
    }
}
