package com.salesorder.microservices.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

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
import org.springframework.stereotype.Component;
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
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import com.salesorder.microservices.domain.Customer;
import com.salesorder.microservices.domain.Item;
import com.salesorder.microservices.domain.SalesOrder;
import com.salesorder.microservices.repository.CustomerSOSRepository;
import com.salesorder.microservices.repository.SalesOrderRepository;
import com.salesorder.microservices.service.SalesOrderService;

@RestController
public class SalesOrderController {

	private Logger logger = LoggerFactory.getLogger(getClass());


	
	@Autowired
	private SalesOrderService salesOrderService;

	@Autowired
	CustomerSOSRepository customerSOSRepository;
	@Autowired
	SalesOrderRepository salesOrderRepository;
	
	
	DBInfo dbinfo;
	
	public SalesOrderController(DBInfo dbinfo){
		this.dbinfo = dbinfo;
	}
	
	@GetMapping("/service3/dbinfo")
	public DBInfo getInfo(){
		return this.dbinfo;
	}
	
	@PostMapping("/service3/orders")
	public ResponseEntity add(@RequestBody SalesOrder salesOrder) {
		
		logger.debug("(salesOrder.getId()))::::::::: "+salesOrder.getId());

		List<Item> itemlist = salesOrderService.fetchItemDetails(salesOrder.getItems());
		
		
		Customer _customer=customerSOSRepository.findOne(salesOrder.getCust_id());
		
		logger.debug("(_customer.getId()))::::::::: "+_customer.getId());
		if(_customer!=null && _customer.getId()!=null) {
			Integer price=salesOrderService.saveItemList(itemlist,salesOrder);
			
			salesOrder.setPrice(price.toString());
			
			salesOrderRepository.saveSalesOrderDtls(salesOrder);

			return new ResponseEntity<List<Item>>(itemlist, HttpStatus.OK);
		}
		else {
			logger.debug("Invalid customer id ::::::::: "+_customer.getId());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	
		
}

@Component
class DBInfo {
	private String url;

	public DBInfo(DataSource dataSource) throws SQLException{
		this.url = dataSource.getConnection().getMetaData().getURL();
	}

	public String getUrl() {
		return url;
	}
}
