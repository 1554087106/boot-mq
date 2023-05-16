package org.whz.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 生产者启动类
 */
@Slf4j
@SpringBootApplication
@EnableScheduling // 开启定时任务
public class ProducerApplication {


	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

}
