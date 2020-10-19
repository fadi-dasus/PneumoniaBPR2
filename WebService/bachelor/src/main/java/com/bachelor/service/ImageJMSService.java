package com.bachelor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bachelor.model.Image;

@Service

public class ImageJMSService implements IJMSService {
private static final String IMAGE_QUEUE = "img";

@Autowired
private JmsTemplate jmsTemplate;

@Transactional
public void send(Image image){
	jmsTemplate.convertAndSend(IMAGE_QUEUE,image);
}
}
