package org.whz.consumer.service;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.whz.consumer.conf.RPCConfig.EXCHANGE_RPC_NAME;
import static org.whz.consumer.conf.RPCConfig.ROUTE_KEY_RPC;

/**
 * @author: hong-zhi
 * @date: 2023/5/6 11:18
 * @Description 模拟RPC的消费者端
 * 需求:消费者端通过RPC服务调用消费者端的斐波那契数列结果
 */
@Slf4j
@Service
public class RPCService {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     *
     * @param message
     */
    @RabbitListener(queues = "hongzhi-rpc-queue")
    public void receiveMessage(String message,
                               MessageProperties properties,
                               Channel channel) {

        log.info("获取到的请求参数: {}\r\nCorrelationId: {}\r\nReplyTo: {}", message, properties.getCorrelationId(), properties.getReplyTo());
//        String correlationId = message.getMessageProperties().getCorrelationId();
//        String replyTo = ROUTE_KEY_RPC;
//        log.info("\r\nRPC调用方传递的参数: {} \r\n " + "路由键名称: {}\r\n replyTo: {}\r\ncorrelationId: {}", message, replyTo, correlationId);

        long result = 11;
        // 处理消息
//        rabbitTemplate.convertAndSend(replyTo, result, new MessagePostProcessor() {
//            @Override
//            public Message postProcessMessage(Message message) throws AmqpException {
//                message.getMessageProperties().setCorrelationId(correlationId);
//                return message;
//            }
//        });
        // 收到确认消息
//        try {
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (IOException e) {
//            log.error("消息确认操作失败: {}", e.getMessage());
//        }

//        // 调用斐波那契数列函数，计算结果
//        long result = fib(message);
//
//        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
//        buffer.putLong(result);
//        byte[] bytes = buffer.array();
//        Message response = new Message(bytes, properties);
//        // 手动确认消息
//        try {
////            // basicAck(long deliveryTag, boolean multiple)
////            // 确认一个或多个已接收的消息
////            // deliveryTag-来自接收到的AMQP.Basic.GetOk或AMQP.Basic.Deliver标记
////            // multiple-true以确认所有消息，直到包括提供的传递标记；false只确认所提供的交付标记。
//            rabbitTemplate.send(EXCHANGE_RPC_NAME, ROUTE_KEY_RPC, new Message(bytes));
////            // TODO 消息确认
//            channel.basicAck(head, false);
//        } catch (IOException e) {
//            log.error("手动确认消息失败，原因: {}", e.getMessage());
//        }
//        return result;
    }

    public long fib(long n) {
        return n == 0 ? 0 : n == 1 ? 1 : (fib(n - 1) + fib(n - 2));
    }
}
