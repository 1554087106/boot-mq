package org.whz.producer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author hongzhi
 * @Date 2021/1/20 16:11
 * @Description
 * Direct 模式的交换机
 */
@Slf4j
@SpringBootTest(classes = ProducerApplication.class)
class DirectExchangeTest {
	@Autowired
	private DirectExchange directExchange;

	@Test
	public void directExchangeTest() {
		log.info("directExchange: {}", directExchange);
	}

}
