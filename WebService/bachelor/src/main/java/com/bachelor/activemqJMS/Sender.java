package com.bachelor.activemqJMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.bachelor.model.Image;

@Component
public class Sender {
    private static final String BOOK_QUEUE = "img";


    @Autowired
    JmsTemplate jmsTemplate;

    public void send(Image message){
        jmsTemplate.convertAndSend(BOOK_QUEUE, message);
    }
}
