package org.whz.producer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.whz.producer.service.ProducerService;

@Slf4j
@SpringBootTest(classes = ProducerApplication.class)
class SendMessageTest {
	@Autowired
	private ProducerService producerService;
	@Autowired
	private AmqpAdmin amqpAdmin;
	@Test
	void sendMessageTest() {
		producerService.sendMessage("TEST QUEUE1");
		producerService.sendMessage2("TEST QUEUE2");
		producerService.sendMessage3("非持久化消息和队列");
		log.info("信息发送完毕!");
	}
	@Test
	public void amqpAdminTest() {
		log.info("amqpAdmin: {}", amqpAdmin);
	}

}
