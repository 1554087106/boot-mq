package org.whz.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 开启定时任务
public class ProducerApplication {


	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
//		applicationContext.getBean(ProducerService.class).sendMessage("hello world");

	}

}
