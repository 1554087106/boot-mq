package org.whz.consumer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = ConsumerApplication.class)
class ConsumerApplicationTests {
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
        log.info("connectionFactory: {}\r\nrabbitTemplate: {}", connectionFactory,rabbitTemplate);
    }

}
