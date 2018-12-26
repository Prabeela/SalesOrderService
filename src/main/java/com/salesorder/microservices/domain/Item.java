package com.salesorder.microservices.domain;

import java.util.Date;

public class Item {

	private String id;
	private String name;
    private String description;
    private String price;
    private Date created;
    private Date modified;
    
    
    public Item() {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
		this.description = description;
		this.price = price;
        this.created = new Date();
        this.modified = new Date();
    }
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

}
