package com.xiaolinzi.rabbitmq.product;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqStudyProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqStudyProductApplication.class, args);
	}

}
