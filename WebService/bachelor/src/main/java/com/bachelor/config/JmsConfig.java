package com.bachelor.config;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@EnableJms
@Configuration
public class JmsConfig {
	 @Autowired
	    private ConnectionFactory connectionFactory;
	
	  @Bean
	    public MessageConverter jacksonJmsMessageConverter(){

	        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	        converter.setTargetType(MessageType.TEXT);
	        converter.setTypeIdPropertyName("_type");
	        return converter;
	    }
	  
//	  @Bean
//	    public ActiveMQConnectionFactory connectionFactory(){
//	        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
//	        return factory;
//	    }
	  @Bean
	    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory( ){
	        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	        factory.setConnectionFactory(connectionFactory);
	        factory.setMessageConverter(jacksonJmsMessageConverter());
	        //factory.setMessageConverter(xmlMarshallingMessageConverter());
	        return factory;
	    }






}
