package com.message;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

@SpringBootApplication
@Configuration
@ComponentScan
public class MessangerApplication {
	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory connectionFactory =
				new ActiveMQConnectionFactory("vm://localhost");
		return connectionFactory;
	}

	public static void main(String[] args) {
		SpringApplication.run(MessangerApplication.class, args);
	}

}
