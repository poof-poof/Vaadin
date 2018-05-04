package com.gp.vaadin.hotel.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@SuppressWarnings("serial")
@Entity
@Table(name="CATEGORY")
public class Category implements Serializable, Cloneable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Version
	@Column(name = "OPTLOCK")
	private int OPT_LOCK;
	
	@Column(name = "NAME", nullable = false)
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
