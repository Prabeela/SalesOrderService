package com.salesorder.microservices.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class SalesOrder {

	private String id;
	private String description;
    private String cust_id;
    private Date orderDate;
    private ArrayList<String> items;
    
    
    public SalesOrder(String description,String cust_id) {
        this.id = java.util.UUID.randomUUID().toString();
        this.description = description;
		this.cust_id = cust_id;
		
      
    }
    
    public SalesOrder() {
    }

    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public ArrayList<String> getItems() {
		return items;
	}
	public void setItems(ArrayList<String> items) {
		this.items = items;
	}
	

}
