package com.gp.vaadin.hotel.view;

import java.util.List;
import java.util.Set;

import com.gp.vaadin.hotel.core.Category;
import com.gp.vaadin.hotel.core.Hotel;
import com.gp.vaadin.hotel.form.CategoryEditForm;
import com.gp.vaadin.hotel.service.CategoryService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class CategoryView extends VerticalLayout implements View {
	
	final CategoryService categoryService = CategoryService.getInstance();
	final Grid<Category> categoryGrid = new Grid<>();
	final Button addCategory = new Button("Add category");
	final Button dellCategory = new Button("Dell category");
	final Button editCategory = new Button("Edit category");
	private CategoryEditForm form = new CategoryEditForm(this, categoryService);
	
	public CategoryView() {
		HorizontalLayout buttons = new HorizontalLayout();
		HorizontalLayout content = new HorizontalLayout();
		
		addComponents(buttons, content);
		
		buttons.addComponents(addCategory, editCategory, dellCategory);
		content.addComponents(categoryGrid, form);
		form.setVisible(false);
		
    	content.setWidth("100%");
    	content.setHeight(250, Unit.PIXELS);
    	content.setExpandRatio(categoryGrid, 0.7f);
    	content.setExpandRatio(form, 0.3f);
    	content.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
    	
    	form.setWidth("80%");
    	form.setHeight("100%");
    	form.setVisible(false);
    	
    	categoryGrid.setWidth(100, Unit.PERCENTAGE);
    	categoryGrid.setHeight(100, Unit.PERCENTAGE); 
		
		dellCategory.setEnabled(false);
		editCategory.setEnabled(false);
		
		categoryGrid.addColumn(Category::toString).setCaption("Category");
		
		categoryGrid.setSelectionMode(SelectionMode.MULTI);
		
		categoryGrid.asMultiSelect().addValueChangeListener(e ->{
    		if (e.getValue() != null) {
    			if (e.getValue().size() == 1) {
    				dellCategory.setEnabled(true);
    				editCategory.setEnabled(true);
    			}else if (e.getValue().size() > 1) {
    				dellCategory.setEnabled(true);
    				editCategory.setEnabled(false);
    			}else {
    				dellCategory.setEnabled(false);
    				editCategory.setEnabled(false);
    			}
    		}
    	});
		
		addCategory.addClickListener(e -> form.setCategory(new Category()));
		
		editCategory.addClickListener(e -> form.setCategory(categoryGrid.getSelectedItems().iterator().next()));
		
		dellCategory.addClickListener(e ->{
    		if (categoryGrid.getSelectedItems().size() == 1) {
    			Category dellCanditate = categoryGrid.getSelectedItems().iterator().next();
        		categoryService.delete(dellCanditate);
    		}else {
    			Set<Category> dellCanditates = categoryGrid.getSelectedItems();
    			for(Category dellCanditate : dellCanditates )
    				categoryService.delete(dellCanditate);
    		}
    		dellCategory.setEnabled(false);
    		updateList();	
    	});
		
		setMargin(false);
		updateList();
	}
	
	public void updateList() {
		List<Category> categoryList = categoryService.getCatigories();
		categoryGrid.setItems(categoryList);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}
