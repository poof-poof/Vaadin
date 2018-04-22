package com.gp.vaadin.hotel.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.gp.vaadin.hotel.core.Category;
import com.gp.vaadin.hotel.core.HotelCategory;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class CategoryService {
	
	private static CategoryService instance;
	public static Category categoryInstance;
	private static final Logger LOGGER = Logger.getLogger(HotelService.class.getName());
	
	private final HashMap<Long, Category> categories = new HashMap<>();
	private long nextId = 0;
	
	public static CategoryService getInstance() {
		if (instance == null) {
			instance = new CategoryService();
			instance.ensureTestData();
		}
		return instance;
	}
	
	public static Category getCategoryInstance() {
		if (categoryInstance == null) {
			categoryInstance = new Category(-1L,"No category");
		}
		return categoryInstance;
	}
	
	public synchronized List<Category> getCatigories(){
		ArrayList<Category> arrayList = new ArrayList<>();
		for (Category category : categories.values()) {
			arrayList.add(category);
		}
		Collections.sort(arrayList, new Comparator<Category>() {
			@Override
			public int compare(Category o1, Category o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}
	
	public synchronized void save(Category entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE, "Category is null.");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		categories.put(entry.getId(), entry);
	}


	public synchronized void delete(Category value) {
		
		categories.remove(value.getId());		
	}
	
	public void ensureTestData() {
		for(HotelCategory category : HotelCategory.values()) {
			Category cat = new Category();
			cat.setCategory(category.name());
			save(cat); 
		}
			
	}

}
