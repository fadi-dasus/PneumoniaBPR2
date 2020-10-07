package com.bachelor.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@EnableJms
@Configuration
public class JmsConfig {
	
	  @Bean
	    public MessageConverter jacksonJmsMessageConverter(){
	        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	        converter.setTargetType(MessageType.TEXT);
	        converter.setTypeIdPropertyName("_type");
	        return converter;
	    }
	  
	  @Bean
	    public ActiveMQConnectionFactory connectionFactory(){
	        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
	        return factory;
	    }
	    @Bean
	    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(){
	        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	        factory.setConnectionFactory(connectionFactory());
//	        factory.setConcurrency("1-1");
	        factory.setMessageConverter(jacksonJmsMessageConverter());
	        return factory;
	    }

//	@Bean
//	public JmsListenerContainerFactory<?> warehouseFactory(ConnectionFactory factory,
//			DefaultJmsListenerContainerFactoryConfigurer configurer) {
//		DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
//		configurer.configure(containerFactory, factory);
//
//		return containerFactory;
//	}




}
