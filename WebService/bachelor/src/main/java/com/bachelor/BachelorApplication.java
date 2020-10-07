package com.bachelor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;

//@EnableJms
@SpringBootApplication
public class BachelorApplication {

	public static void main(String[] args) {
//		SpringApplication.run(BachelorApplication.class, args);

		ConfigurableApplicationContext context = SpringApplication.run(BachelorApplication.class, args);
//		Sender sender = context.getBean(Sender.class);
//		sender.sendMessage("order-queue", "\n############\nHello From Spring\n #########");

	}

	

}
