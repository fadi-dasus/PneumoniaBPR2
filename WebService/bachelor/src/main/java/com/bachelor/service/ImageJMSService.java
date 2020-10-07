package com.bachelor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.bachelor.model.Image;

@Service
public class ImageJMSService {
private static final String IMAGE_QUEUE = "img";

@Autowired
private JmsTemplate jmsTemplate;

public void send(Image image){
	jmsTemplate.convertAndSend(IMAGE_QUEUE,image);
}
}
