package org.whz.producer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.MessageHeaders;

@Slf4j
@SpringBootTest(classes = ProducerApplication.class)
class RPCMessageTest {
//	@Autowired
//	private MessageHeaders messageHeaders;
//	@Test
//	public void messageHeadersTest() {
//		log.info("/r/ncorrelationId: {}\r\nreplyTo: {}",
//				messageHeaders.get("correlationId", String.class),
//				messageHeaders.get("replyTo", String.class));
//	}

}
