package com.salesorder.microservices.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class SalesOrder {

	private String id;
	private String description;
    private String cust_id;
    
	private ArrayList<Integer> quantity;
    private Date orderDate;
    private String price;
    private ArrayList<String> items;
    
    
    public SalesOrder() {
        this.id = java.util.UUID.randomUUID().toString();
        this.description = description;
		this.cust_id = cust_id;
		this.orderDate=orderDate;
		this.items=items;
		this.quantity=quantity;
		System.out.println("Insided args constructor");
    }
    
    
    public ArrayList<Integer> getQuantity() {
		return quantity;
	}

	public void setQuantity(ArrayList<Integer> quantity) {
		this.quantity = quantity;
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
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public ArrayList<String> getItems() {
		return items;
	}
	public void setItems(ArrayList<String> items) {
		this.items = items;
	}
	

}
