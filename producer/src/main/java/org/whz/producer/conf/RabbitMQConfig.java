//package org.whz.producer.conf;
//
//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author: hong-zhi
// * @date: 2023/5/6 15:58
// * @Description RabbitMQ任务调度模式 轮询调度 -> 公平调度
// * 通过设置 prefetchCount 属性来实现公平调度
// * 每个消费者在接收消息之前 先请求一定数量的消息，这些消息会存储在消费者的缓存区中
// * 当消费者完成处理其中一条消息后，会发送一个确认(ack)消息告诉RabbitMQ，然后再从缓存区中获取下一条信息
// * 如果某个消费者的缓冲区已经有来很多未处理的消息，而其他消费者的缓冲区却很少或为空
// * 那么该消费者就会持续地消费这些消息，而其他消费者无法获取到消息，从而导致不公平的调度
// * 为了避免这种情况，可以通过设置prefetchCount 属性来限制每个消费者一次性获取消息的数量
// *
// */
////@Configuration
////@EnableRabbit
//public class RabbitMQConfig {
//    @Autowired
//    private ConnectionFactory connectionFactory;
//
//    @Autowired
//    private Queue queue1;
//
//
//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        /**
//         * 设置每个消费者一次性获取消息数量为1
//         */
//        container.setConnectionFactory(connectionFactory);
//        container.setQueues(queue1);
//        container.setPrefetchCount(1);
//        return container;
//    }
//}
