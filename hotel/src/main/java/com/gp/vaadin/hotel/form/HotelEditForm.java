package com.gp.vaadin.hotel.form;

import java.time.LocalDate;

import com.gp.vaadin.hotel.core.Category;
import com.gp.vaadin.hotel.core.Hotel;
import com.gp.vaadin.hotel.core.HotelCategory;
import com.gp.vaadin.hotel.service.CategoryService;
import com.gp.vaadin.hotel.service.HotelService;
import com.gp.vaadin.hotel.view.HotelView;
import com.vaadin.data.Binder;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class HotelEditForm extends FormLayout {
	
	private HotelView uv;
	private HotelService hotelService = HotelService.getInstance();
	private CategoryService categoryService = CategoryService.getInstance();
	private Hotel hotel;
	private Binder<Hotel> binder = new Binder<>(Hotel.class);
	
	private TextField name = new TextField("Name:");
	private TextField address = new TextField("Addres:");
	private TextField rating = new TextField("Rating:");
	private DateField operatesFrom = new DateField("Date:");
	private NativeSelect<Category> category = new NativeSelect<>("Catrgory:");
	private TextField url = new TextField("Url:");
	private TextArea description = new TextArea("Description:");
	
	private Button save = new Button("Save");
	private Button close = new Button("Close");
	
	public HotelEditForm(HotelView hotelView){
		this.uv = hotelView;
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponents(save,close);
		
		name.setWidth(100, Unit.PERCENTAGE);
		address.setWidth(100, Unit.PERCENTAGE);
		rating.setWidth(100, Unit.PERCENTAGE);
		operatesFrom.setWidth(100, Unit.PERCENTAGE);
		category.setWidth(100, Unit.PERCENTAGE);
		url.setWidth(100, Unit.PERCENTAGE);
		description.setWidth(100, Unit.PERCENTAGE);
		save.setWidth(100, Unit.PERCENTAGE);
		close.setWidth(100, Unit.PERCENTAGE);
		buttons.setWidth(100, Unit.PERCENTAGE);
		
		addComponents(name, address, rating, operatesFrom, category, description, url, buttons);
		
		updateCategory();
		
		prepareFields();
		
		save.addClickListener(e -> save());
		close.addClickListener(e -> setVisible(false));
		
		this.setMargin(false);
		
	}

	private void save() {
		if (binder.isValid()) {
			try {
				binder.writeBean(hotel);
			} catch (ValidationException e) {
				Notification.show("Unable to save!" + e.getMessage(),Type.ERROR_MESSAGE);
			}
			hotelService.save(hotel);
			exit();
		} else {
			Notification.show("Unable to save! Please review errors and fix them.",Type.ERROR_MESSAGE);
		}
	}
	
	public Hotel getHotel() {
		return hotel;
	}
	
	public void setHotel(Hotel hotel) {
		updateCategory();
		this.hotel = hotel;
		binder.readBean(this.hotel);
		setVisible(true);
	}
	
	private void exit() {
		setVisible(false);
		uv.updateList();
	}
	private void updateCategory() {
		category.setItems(categoryService.getCatigories());
	}
	
	@SuppressWarnings("serial")
	public void prepareFields() {
		
		Validator<String> addressValidator = new Validator<String>() {
			@Override
			public ValidationResult apply(String value, ValueContext context) {
				if (value == null || value.isEmpty())
					return ValidationResult.error("The address is empty");
				else if (value.length() < 5)
					return ValidationResult.error("The address is too short");
				else return ValidationResult.ok();
			}
		};
		
		Validator<String> ratingValidator = new Validator<String>() {
			@Override
			public ValidationResult apply(String value, ValueContext context) {
				if (value == null || value.isEmpty())
					return ValidationResult.error("The rating is empty");
				else if (Integer.parseInt(value) < 0 || Integer.parseInt(value) > 5)
					return ValidationResult.error("The raiting is incorrect");
				else return ValidationResult.ok();
			}
		};

		binder.forField(name).asRequired("Pleas enter a name").bind(Hotel::getName,Hotel::setName);
		binder.forField(address).withValidator(addressValidator).asRequired("Pleas enter a address").bind(Hotel::getAddress,Hotel::setAddress);
		binder.forField(rating).withValidator(ratingValidator).asRequired("Pleas enter a raiting").bind(Hotel::getRating,Hotel::setRating);
		binder.forField(operatesFrom)
		.asRequired("Pleas enter a date")
		.withConverter(localDate -> ((LocalDate)localDate).toEpochDay(),aLong -> (aLong == 0L) ? LocalDate.now().minusDays(1) : LocalDate.ofEpochDay(aLong))
		.withValidator(aLong -> {return aLong < LocalDate.now().toEpochDay();},"Incorrect date!")
		.bind(Hotel::getOperatesFrom,Hotel::setOperatesFrom);		
		binder.forField(category).asRequired("Pleas select a category").withNullRepresentation(CategoryService.getCategoryInstance()).bind(Hotel::getCategory,Hotel::setCategory);
		binder.forField(url).asRequired("Pleas enter a url").bind(Hotel::getUrl,Hotel::setUrl);
		binder.forField(description).bind(Hotel::getDescription,Hotel::setDescription);
		name.setDescription("Hotel name");
		address.setDescription("Hotel address");
		rating.setDescription("Hotel rating from 0 to 5");
		operatesFrom.setDescription("Hotel operates from");
		category.setDescription("Hotel category");
		url.setDescription("Hotel url");
		description.setDescription("Hotel description");
		
	}
	

}
