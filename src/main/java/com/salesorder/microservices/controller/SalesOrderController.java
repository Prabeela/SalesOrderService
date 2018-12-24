package com.salesorder.microservices.controller;


import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.netflix.appinfo.InstanceInfo;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.salesorder.microservices.domain.SalesOrder;

@Controller
public class SalesOrderController {
	
	 private Logger logger = LoggerFactory.getLogger(getClass());
	 @Autowired
	 private DiscoveryClient discoveryClient;
	
	@PostMapping("/service3/orders")
    public String add(@RequestBody SalesOrder salesOrder) {
		
		 List<ServiceInstance> instances=discoveryClient.getInstances("ITEM-SERVICE-541455");
			ServiceInstance serviceInstance=instances.get(0);
			
			String baseUrl=serviceInstance.getUri().toString();
			
			baseUrl=baseUrl+"/service2/items";
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response=null;
			try{
			response=restTemplate.exchange(baseUrl,
					HttpMethod.GET, getHeaders(),String.class);
			}catch (Exception ex)
			{
				System.out.println(ex);
			}
			System.out.println(response.getBody());

			System.out.println(response.getBody());
			return "success";
		
    	
    }
	
	 private void fetchItemServiceUrl() {
		 
		
	    }
	
	 
	 private static HttpEntity<?> getHeaders() throws IOException {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			return new HttpEntity<>(headers);
		}
	
	/*
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerServiceProducer customerServiceProducerEvent;

    @GetMapping("/service1/customers")
    public List<Customer> snippets() {
        return customerRepository.findAll();
    }

    @GetMapping("/service1/customer/{id}")
    public Customer customer(@PathVariable("id") String id) {
        return customerRepository.findOne(id);
    }

    @PostMapping("/service1/customer")
    public ResponseEntity<?> add(@RequestBody Customer customer) {
    	Customer _customer = customerRepository.save(customer);
        assert _customer != null;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/" + _customer.getId())
                .buildAndExpand().toUri());

       
        customerServiceProducerEvent.createCustomerEvent(_customer);
        return new ResponseEntity<>(_customer, httpHeaders, HttpStatus.CREATED);
    }
    
    */
}

