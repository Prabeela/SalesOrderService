package com.salesorder.microservices.service;

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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import com.salesorder.microservices.domain.Customer;
import com.salesorder.microservices.domain.Item;
import com.salesorder.microservices.domain.SalesOrder;
import com.salesorder.microservices.repository.CustomerSOSRepository;
import com.salesorder.microservices.repository.SalesOrderRepository;

@Service
public class SalesOrderService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Autowired
//	private DiscoveryClient discoveryClient;
	
	@Autowired
	  private RestTemplate restTemplate;
	
	@Autowired
	  private LoadBalancerClient loadBalancerClient;

	@Value("${itemServiceAppName}")
	private String itemServiceAppName;

	@Value("${customerServiceAppName}")
	private String customerServiceAppName;


	@Autowired
	SalesOrderRepository salesOrderRepository;
	

	
	public Integer saveItemList(List<Item> itemList,SalesOrder salesOrder) {
		
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
	
	
	private String fetchServiceUrl(String appName) {
	    ServiceInstance instance = loadBalancerClient.choose(appName);

	    logger.debug("uri: {}", instance.getUri().toString());
	    logger.debug("serviceId: {}", instance.getServiceId());

	    return instance.getUri().toString();
	  }
	
	
	@HystrixCommand(fallbackMethod = "defaultItem")
	public List<Item> fetchItemDetails(ArrayList<String> itemNameList) {
		 
		String baseUrl = fetchServiceUrl(itemServiceAppName);

		System.out.println("Fetching details of item::" + itemNameList.size());

		List<Item> itemList = new ArrayList<Item>();
		for (int i = 0; i < itemNameList.size(); i++) {
			System.out.println("Fetching details of item::" + itemNameList.get(i));

			String itemUrl = baseUrl + "/service2/item/" + itemNameList.get(i);

			//RestTemplate restTemplate = new RestTemplate();
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

	
	 public String defaultItem() {
		    logger.debug("Default Item used.");
		    return "Item Service is not reachable. Try another.";
		  }

	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

	
}
