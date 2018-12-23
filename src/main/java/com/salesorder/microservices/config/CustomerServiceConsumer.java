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
import org.springframework.amqp.core.Queue;

@Service
public class CustomerServiceConsumer {

	@Autowired
	CustomerSOSRepository customerSOSRepository;
	
	@RabbitListener(queues = "spring-boot")
	public void receiveMessage(CustomQueueMessage custMsg) {
		System.out.println("Inside Listener");
		System.out.println("Received <" + custMsg.getText()+"name::"+custMsg.getCustomer().getFirst_name()+">");
		Customer _customer = customerSOSRepository.save(custMsg.getCustomer());
	}
	 
	 
	 


	}