package com.gp.vaadin.hotel.core;

import java.io.Serializable;
import java.time.LocalDate;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;

@SuppressWarnings("serial")
public class Hotel implements Serializable, Cloneable {

	private Long id;

	private String name = "";

	private String address = "";

	private Integer rating = 0;

	private Long operatesFrom = 0L;

	private Category category;
	
	private String url;
	
	private String description = "";

	public boolean isPersisted() {
		return id != null;
	}

	@Override
	public String toString() {
		return name + " " + rating +"stars " + address;
	}

	@Override
	public Hotel clone() throws CloneNotSupportedException {
		return (Hotel) super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public Hotel() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRating() {
		return rating.toString();
	}

	public void setRating(String rating) {
		this.rating = Integer.parseInt(rating);
	}

	public LocalDate getOperatesFrom() {
		return (operatesFrom == 0L) ?  LocalDate.now().minusDays(1) : LocalDate.ofEpochDay(operatesFrom);
	}

	public void setOperatesFrom(LocalDate operatesFrom) {
		this.operatesFrom = operatesFrom.toEpochDay();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Hotel(Long id, String name, String address, String rating, LocalDate operatesFrom, Category category, String url, String description) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.rating = Integer.parseInt(rating);
		this.operatesFrom = operatesFrom.toEpochDay();
		this.category = category;
		this.url = url;
		this.description = description;
	}

}