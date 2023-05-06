### 一.AMQP核心概念

### 二.安装和入门

### 三.RabbitMQ核心API

#### 消息队列持久化

SpringBoot集成RabbitMQ默认持久化Channel和消息

#### channel提前关闭

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



#### 轮询调度和公平调度

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



### 四.RabbitMQ高级特性

