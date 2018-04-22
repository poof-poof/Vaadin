package com.gp.vaadin.hotel.form;

import com.gp.vaadin.hotel.core.Category;
import com.gp.vaadin.hotel.core.Hotel;
import com.gp.vaadin.hotel.service.CategoryService;
import com.gp.vaadin.hotel.view.CategoryView;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;

public class CategoryEditForm extends FormLayout {

	private CategoryService categoryService;
	private CategoryView cv;
	
	private TextField nameCategory = new TextField("Category:");
	private Button save = new Button("Save");
	private Button close = new Button("Close");
	private Category category;
	private Binder<Category> binder = new Binder<>(Category.class);
	
	public CategoryEditForm(CategoryView cv, CategoryService categoryService) {
		this.cv = cv;
		this.categoryService = categoryService;
		
		HorizontalLayout buttons = new HorizontalLayout();
		VerticalLayout content = new VerticalLayout();
		
		buttons.addComponents(save, close);
		
		save.addClickListener(e -> save());
		close.addClickListener(e -> exit());
		
		content.addComponents(nameCategory, buttons);
		
		binder.forField(nameCategory).asRequired("Pleas select a category").bind(Category::getCategory,Category::setCategory);
		nameCategory.setDescription("Category");
		
		addComponent(content);
	}
	
	private void save() {
		if (binder.isValid()) {
			try {
				binder.writeBean(category);
			} catch (ValidationException e) {
				Notification.show("Unable to save!" + e.getMessage(),Type.ERROR_MESSAGE);
			}
			categoryService.save(category);
			exit();
		} else {
			Notification.show("Unable to save! Please review errors and fix them.",Type.ERROR_MESSAGE);
		}
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
		binder.readBean(this.category);
		setVisible(true);
	}
	
	private void exit() {
		setVisible(false);
		cv.updateList();
	}
		
}
