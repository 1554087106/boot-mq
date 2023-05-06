package org.whz.producer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Slf4j
@SpringBootTest(classes = ProducerApplication.class)
class ProducerApplicationTests {
	@Autowired
	private ConnectionFactory connectionFactory;

	@Test
	void contextLoads() {
		log.info("connectionFactory: {}", connectionFactory);
	}

}
