### 一.AMQP核心概念

- `Broker`	`RabbitMQ服务器实例称为`Broker`
- `Virtual Host`
- `Exchange`  包含四种类型`Direct`,`Topic`,`Headers`,`Fanout`
- `Binding`
- `Producer` 消息生产者，将消息发送到`Exchange`中
- `Consumer` 消费者，从Queue中消费信息
- `Routing Key` 交换器与队列绑定规则的一部分
- `Acknowledgement`
- `DeadLetterExchange`
- `TTL`

### 二.安装和入门

### 三.RabbitMQ核心API

#### 1.消息队列持久化

SpringBoot集成RabbitMQ默认持久化Channel和消息

#### 2.channel提前关闭

在使用`spring-boot-starter-amqp`的时候，默认情况下，每个`@RabbitListener`方法会创建一个新的channel来消费消息，这些消息消费完成后会被关闭，从而导致`backAck`手动确认/取消消息等方法无法正常执行。报错`Channel closed; cannot ack/nack`

使用`CachingConnectionFactory` 缓存`Channel`会在使用完毕后自动返回给缓存池，无需手动关闭`channel`。

```java
@Autowired
private CachingConnectionFactory connectionFactory; // 配置缓存池
@Bean
public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 开启手动确认方式
    return factory;
}
```



#### 3.轮询调度和公平调度

使用`spring-boot-starter-amqp`，默认情况下调度模式为轮询，通过配置将模式改为公平调度。

1. 设置消费者数量 设置`SimpleRabbitListenerContainerFactory..setConcurrentConsumers(1)`并发环境下创建的最小消费者数量
2. 设置预取计数 `SimpleRabbitListenerContainerFactory.setPrefetchCount(1)` 预取计数
3. 设置手动确认模式 SimpleRabbitListenerContainerFactory.`setAcknowledgeMode(AcknowledgeMode.MANUAL)`
4. 手动确认消息

```java
@Configuration
public class RabbitMQConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory; // 使用缓存池
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 设置消费者数量
        factory.setConcurrentConsumers(1);
        // 预先处理消息的数量
        factory.setPrefetchCount(1);
        return factory;
    }
}
```

```java
@Service
public class ConsumerService {

    @RabbitListener(queues = "hongzhi-queue",  containerFactory = "rabbitListenerContainerFactory")
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
    }
}
```

#### 4.发布订阅方式

- 交换机的类型有:`direct`,`topic`,`headers`,`fanout`,借助`fanout`完成发布订阅功能，`fanout`模式忽略了绑定键(`routingKey`)的值
- 类`AnonymousQueue` 具有**随机名称** **空白** **非持久化** **独占** **自动删除** 的特征
- 消费者向交换机发布消息，所有订阅了该交换机的队列都会收到该消息。消费者监听队列，有新消息发布，将处理消息。
- 生产者代码

```java
@Configuration
public class RabbitMQQueueConfig {
    @Bean
    public Queue anonymousQueue1() {
        return new AnonymousQueue();
    }
     /**
     * 交换机的fanout模式
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT_NAME);
    }
     /**
     * 绑定交换机和队列
     * @return
     */
    public Binding binding() {
        return BindingBuilder.bind(anonymousQueue1()).to(fanoutExchange());
    }
}
```

```java
@Service
public class MessagePublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(String message) {
        log.info("向交换机发布消息");
      	/**
         * 传三个参数: String exchange, String routingKey, final Object object
         * exchange: 交换机名称
         * routingKey: 路由键  
         * object: 消息体
         */ 
        rabbitTemplate.convertAndSend(EXCHANGE_FANOUT_NAME, "", "向交换机发布 -> " + message);
    }
}
```

#### 5.路由键

- 使用直连交换机(`Direct`)，消息会被发送到绑定键和消息路由键完全匹配的队列中。

- 路由键发送和Queue发送消息：
    -  使用路由键发送消息是通过将消息发送到特定的交换机，并在该交换机上使用特定的路由键来达到目的。交换机根据路由键将消息路由到相应的队列。这种方式允许消息路由到多个队列，实现消息的分发和广播。
    - 直接发送消息到队列则不需要交换机，直接将消息发布到指定的队列。这种方式适合一对一的消息发送，即每个消息只会被发送到一个队列

### 四.RabbitMQ高级特性

