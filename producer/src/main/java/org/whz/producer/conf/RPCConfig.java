package org.whz.producer.conf;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用Rabbit实现一个简单的交换机
 * 配置文件
 */
@Configuration
public class RPCConfig {

    public static final String QUEUE_RPC_NAME = "hongzhi-rpc-queue";
    public static final String EXCHANGE_RPC_NAME = "hongzhi-rpc-exchange";
    public static final String ROUTE_KEY_RPC = "hongzhi-rpc";

    /**
     * 创建队列 hongzhi-rpc-queue
     * @return
     */
    @Bean(name = "hongzhi-rpc-queue")
    public Queue queue1() {
        return new Queue(QUEUE_RPC_NAME);
    }


    @Bean("hongzhi-rpc-exchange")
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_RPC_NAME);
    }

    /**
     * 绑定交换机和队列
     * @return
     */
    @Bean(name = "rpc000")
    public Binding binding9() {
        return BindingBuilder
                .bind(queue1())
                .to(directExchange())
                // 路由键 hongzhi-rpc
                .with(ROUTE_KEY_RPC);
    }
}
