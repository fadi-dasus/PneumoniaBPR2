//package com.bachelor.activemqJMS;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Component;
//
//import com.bachelor.model.Image;
//
//@Component
//public class Receiver {
//	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
//
//	@JmsListener(destination = "img")
//	public void receiveMessage(Image img) {
//		System.out.println("###################################################################################");
//		LOGGER.info("Image Recieved: " + img);
//		System.out.println("############################################################################");
////		throw new RuntimeException();
//	}
//
//}