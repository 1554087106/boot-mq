package org.whz.producer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = ProducerApplication.class)
class FanoutExchangeTest {
//	@Autowired
	private FanoutExchange fanoutExchange;

	@Test
	public void fanoutExchangeTest() {
		log.info("fanoutExchange: {}", fanoutExchange);
	}

}
