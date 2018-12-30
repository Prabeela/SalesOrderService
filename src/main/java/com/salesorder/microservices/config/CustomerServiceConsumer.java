package com.salesorder.microservices.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.salesorder.microservices.domain.CustomQueueMessage;
import com.salesorder.microservices.domain.Customer;
import com.salesorder.microservices.repository.CustomerSOSRepository;
import com.rabbitmq.client.AMQP.Exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;

@Service
public class CustomerServiceConsumer {

	@Autowired
	CustomerSOSRepository customerSOSRepository;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RabbitListener(queues = "spring-boot-customer-541455")
	public void receiveMessage(CustomQueueMessage custMsg) {
		logger.debug("Inside Listener");
		logger.debug("Received <" + custMsg.getText()+"name::"+custMsg.getCustomer().getFirst_name()+">");
		Customer _customer = customerSOSRepository.save(custMsg.getCustomer());
	}
	 
	 
	 


	}