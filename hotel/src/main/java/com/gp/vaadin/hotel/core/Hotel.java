package com.gp.vaadin.hotel.core;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;

@SuppressWarnings("serial")
@Entity
@Table(name="HOTEL")
public class Hotel implements Serializable, Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Version
	@Column(name = "OPTLOCK")
	private int OPT_LOCK;
	
	@Column(name = "NAME", nullable = false)
	private String name = "";

	@Column(name = "ADDRESS", nullable = false)
	private String address = "";

	@Column(name = "RATING", nullable = false)
	private Integer rating = 0;

	@Column(name = "OPERATES_FROM", nullable = false)
	private Long operatesFrom = 0L;

	@Column(name = "CATEGORY", nullable = false)
	private Category category;
	
	@Column(name = "URL", nullable = false)
	private String url;
	
	@Column(name = "DESCRIPTION", nullable = false)
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

	public long getOperatesFrom() {
		return operatesFrom;
	}

	public void setOperatesFrom(long operatesFrom) {
		this.operatesFrom = operatesFrom;
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

	public Hotel(Long id, String name, String address, String rating, long operatesFrom, Category category, String url, String description) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.rating = Integer.parseInt(rating);
		this.operatesFrom = operatesFrom;
		this.category = category;
		this.url = url;
		this.description = description;
	}

}