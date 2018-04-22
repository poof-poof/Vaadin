package com.gp.vaadin.hotel.core;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Category implements Serializable, Cloneable {
	
	private Long id;
	
	private String category = ""; 
	
	@Override
	public String toString() {
		return category;
	}

	@Override
	public Category clone() throws CloneNotSupportedException {
		return (Category) super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	public Category() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	public Category(Long id, String category) {
		super();
		this.id = id;
		this.category = category;
	}

}
