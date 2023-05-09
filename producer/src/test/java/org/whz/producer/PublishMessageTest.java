package org.whz.producer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.whz.producer.service.MessagePublisher;

/**
 * 发布一条消息
 * 交换机名称 -> hongzhi-fanout-exchange
 */
@Slf4j
@SpringBootTest(classes = ProducerApplication.class)
class PublishMessageTest {
	@Autowired
	private MessagePublisher messagePublisher;
	@Test
	void publishMessageTest() {
		log.info("messagePublisher: {}", messagePublisher);
		messagePublisher.publish("我要向交换机《hongzhi-fanout-exchange》发布消息");
		log.info("信息发布完毕!");
	}


}
