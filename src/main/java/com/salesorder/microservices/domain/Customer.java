package com.salesorder.microservices.domain;

import java.io.Serializable;
import java.util.Date;



public class Customer implements Serializable {

	private String id;
	private String email;
    private String first_name;
    private String last_name;
    private Date created;
    private Date modified;
    
    
    public Customer() {
        this.id = java.util.UUID.randomUUID().toString();
        this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
        this.created = new Date();
        this.modified = new Date();
    }
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
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
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", email=" + email + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", created=" + created + ", modified=" + modified + "]";
	}

}
