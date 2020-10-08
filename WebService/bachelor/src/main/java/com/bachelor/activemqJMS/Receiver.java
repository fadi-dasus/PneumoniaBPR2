package com.bachelor.activemqJMS;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.bachelor.model.Image;

@Component
public class Receiver {

	@JmsListener(destination = "img")
	public void receiveMessage(Image img) {
		System.out.println("Image Recieved = " + img);
//		throw new RuntimeException();
	}

}