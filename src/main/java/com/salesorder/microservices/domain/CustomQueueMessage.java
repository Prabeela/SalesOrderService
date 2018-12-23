package com.salesorder.microservices.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomQueueMessage implements Serializable {
	
	private String text;
	private Customer customer;
	
	public CustomQueueMessage(@JsonProperty("text")String text, @JsonProperty("customer")Customer customer) {
		super();
		this.text = text;
		this.customer = customer;
	}

	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public String toString() {
		return "CustomQueueMessage [text=" + text + ", customer=" + customer.toString() + "]";
	}
	
}
