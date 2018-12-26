package com.salesorder.microservices.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.salesorder.microservices.domain.Customer;
import com.salesorder.microservices.domain.Item;
import com.salesorder.microservices.domain.SalesOrder;
import com.salesorder.microservices.repository.CustomerSOSRepository;
import com.salesorder.microservices.repository.SalesOrderRepository;

@RestController
public class SalesOrderController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DiscoveryClient discoveryClient;

	@Value("${itemServiceAppName}")
	private String itemServiceAppName;

	@Value("${customerServiceAppName}")
	private String customerServiceAppName;

	
	
	@Autowired
	CustomerSOSRepository customerSOSRepository;
	@Autowired
	SalesOrderRepository salesOrderRepository;
	
	@PostMapping("/service3/orders")
	public ResponseEntity add(@RequestBody SalesOrder salesOrder) {
		
		System.out.println("(salesOrder.getId()))::::::::: "+salesOrder.getId());

		List<Item> itemlist = fetchItemDetails(salesOrder.getItems());
		
		Customer _customer=customerSOSRepository.findOne(salesOrder.getCust_id());
		
		if(_customer!=null && _customer.getId()!=null) {
			Integer price=saveItemList(itemlist,salesOrder);
			
			salesOrder.setPrice(price.toString());
			
			salesOrderRepository.saveSalesOrderDtls(salesOrder);

			return new ResponseEntity<List<Item>>(itemlist, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	
	private Integer saveItemList(List<Item> itemList,SalesOrder salesOrder) {
		
		Integer price=0;
		
		for (int i = 0; i < itemList.size(); i++) {
			
		
			if(salesOrder.getItems().get(i).equals(itemList.get(i).getName())) {
					price=price+salesOrder.getQuantity().get(i)*Integer.parseInt(itemList.get(i).getPrice());
					
					
					salesOrderRepository.saveOrderLineItemDtls(salesOrder,itemList.get(i),salesOrder.getQuantity().get(i).toString());
			}
			else {
				for(int j=i+1;j<salesOrder.getItems().size();j++) {
					
				
					if(salesOrder.getItems().get(j).equals(itemList.get(i).getName()))
						price=price+salesOrder.getQuantity().get(j)*Integer.parseInt(itemList.get(i).getPrice());
					
						salesOrderRepository.saveOrderLineItemDtls(salesOrder,itemList.get(i),salesOrder.getQuantity().get(j).toString());
				}
			}
		}
		return price;
	}
	private List<Item> fetchItemDetails(ArrayList<String> itemNameList) {

		String baseUrl = fetchBaseURL(itemServiceAppName);

		System.out.println("Fetching details of item::" + itemNameList.size());

		List<Item> itemList = new ArrayList<Item>();
		for (int i = 0; i < itemNameList.size(); i++) {
			System.out.println("Fetching details of item::" + itemNameList.get(i));

			String itemUrl = baseUrl + "/service2/item/" + itemNameList.get(i);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Item> response = null;
			try {
				response = restTemplate.exchange(itemUrl, HttpMethod.GET, getHeaders(), Item.class);
			} catch (NullPointerException e) {
				System.out.print("NullPointerException Caught");
			} catch (Exception ex) {
				// System.out.println(ex);

			}

			if (response != null && response.hasBody() && response.getBody() != null) {
				System.out.println("not null");
				System.out.println(response.getBody());
				itemList.add(response.getBody());
			} 

		}
		return itemList;
	}

	
	private String fetchBaseURL(String servicename) {
		List<ServiceInstance> instances = discoveryClient.getInstances(servicename);
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString();
		return baseUrl;
	}

	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

	/*
	 * @Autowired CustomerRepository customerRepository;
	 * 
	 * @Autowired CustomerServiceProducer customerServiceProducerEvent;
	 * 
	 * @GetMapping("/service1/customers") public List<Customer> snippets() { return
	 * customerRepository.findAll(); }
	 * 
	 * @GetMapping("/service1/customer/{id}") public Customer
	 * customer(@PathVariable("id") String id) { return
	 * customerRepository.findOne(id); }
	 * 
	 * @PostMapping("/service1/customer") public ResponseEntity<?> add(@RequestBody
	 * Customer customer) { Customer _customer = customerRepository.save(customer);
	 * assert _customer != null;
	 * 
	 * HttpHeaders httpHeaders = new HttpHeaders();
	 * httpHeaders.setLocation(ServletUriComponentsBuilder
	 * .fromCurrentRequest().path("/" + _customer.getId())
	 * .buildAndExpand().toUri());
	 * 
	 * 
	 * customerServiceProducerEvent.createCustomerEvent(_customer); return new
	 * ResponseEntity<>(_customer, httpHeaders, HttpStatus.CREATED); }
	 * 
	 */
}
