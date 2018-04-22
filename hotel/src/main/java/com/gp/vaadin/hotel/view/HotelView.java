package com.gp.vaadin.hotel.view;

import java.util.List;
import java.util.Set;

import com.gp.vaadin.hotel.core.Hotel;
import com.gp.vaadin.hotel.form.HotelEditForm;
import com.gp.vaadin.hotel.service.HotelService;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.renderers.HtmlRenderer;

public class HotelView extends VerticalLayout implements View {
	
	final HotelService hotelService = HotelService.getInstance();
	final Grid<Hotel> hotelGrid = new Grid<>();
	final TextField filterName = new TextField();
	final TextField filterAddress = new TextField();
	final Button addHotel = new Button("Add hotel");
	final Button dellHotel = new Button("Delete hotel");
	final Button editHotel = new Button("Edit hotel");
	private HotelEditForm form = new HotelEditForm(this);
	
	public HotelView() {
    	HorizontalLayout controls = new HorizontalLayout();
    	HorizontalLayout content = new HorizontalLayout();

    	controls.addComponents(filterName,filterAddress,addHotel,editHotel,dellHotel);
    	
    	content.addComponents(hotelGrid, form);
    	
    	content.setWidth("100%");
    	content.setHeight(468, Unit.PIXELS);
    	content.setExpandRatio(hotelGrid, 0.7f);
    	content.setExpandRatio(form, 0.3f);
    	content.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
    	
    	form.setWidth("80%");
    	form.setHeight("100%");
    	form.setVisible(false);
    	
    	hotelGrid.setWidth(100, Unit.PERCENTAGE);
    	hotelGrid.setHeight(100, Unit.PERCENTAGE);
    	
    	dellHotel.setEnabled(false);
    	editHotel.setEnabled(false);

    	addComponents(controls,content);
    	setMargin(false);

    	//data
    	hotelGrid.addColumn(Hotel::getName).setCaption("Name");
    	hotelGrid.addColumn(Hotel::getAddress).setCaption("Address");
    	hotelGrid.addColumn(Hotel::getRating).setCaption("Rating");
    	hotelGrid.addColumn(Hotel::getCategory).setCaption("Category");
    	hotelGrid.addColumn(Hotel::getOperatesFrom).setCaption("Operates From");
    	hotelGrid.addColumn(Hotel::getDescription).setCaption("Description");
    	Grid.Column<Hotel,String> htmlColumn = hotelGrid.addColumn(
    			hotel ->  "<a href='" + hotel.getUrl() + "' target='_blank'>Hotel info</a>", new HtmlRenderer());
    	htmlColumn.setCaption("Url");
    	
    	filterName.setPlaceholder("filter by name");
    	filterAddress.setPlaceholder("filter by address");
    	
        filterName.addValueChangeListener(e -> updateList());
        filterAddress.addValueChangeListener(e -> updateList());
        
        filterName.setValueChangeMode(ValueChangeMode.LAZY);
        filterAddress.setValueChangeMode(ValueChangeMode.LAZY);
        
    	hotelGrid.setSelectionMode(SelectionMode.MULTI);
    	
    	hotelGrid.asMultiSelect().addValueChangeListener(e ->{
    		if (e.getValue() != null) {
    			if (e.getValue().size() == 1) {
        			dellHotel.setEnabled(true);
        			editHotel.setEnabled(true);
    			}else if (e.getValue().size() > 1) {
        			dellHotel.setEnabled(true);
        			editHotel.setEnabled(false);
    			}else {
        			dellHotel.setEnabled(false);
        			editHotel.setEnabled(false);
    			}
    		}
    	});
    	
    	addHotel.addClickListener(e -> form.setHotel(new Hotel()));
    	
    	editHotel.addClickListener(e -> form.setHotel(hotelGrid.getSelectedItems().iterator().next()));
    	
    	dellHotel.addClickListener(e ->{
    		if (hotelGrid.getSelectedItems().size() == 1) {
    			Hotel dellCanditate = hotelGrid.getSelectedItems().iterator().next();
        		hotelService.delete(dellCanditate);
    		}else {
    			Set<Hotel> dellCanditates = hotelGrid.getSelectedItems();
    			for(Hotel dellCanditate : dellCanditates )
    				hotelService.delete(dellCanditate);
    		}
    		dellHotel.setEnabled(false);
    		updateList();	
    	});
        
        updateList();
	}
	
    public void updateList() {
    	List<Hotel> hotelList = hotelService.findAll(filterName.getValue(),filterAddress.getValue());
    	hotelGrid.setItems(hotelList);
	}
}
