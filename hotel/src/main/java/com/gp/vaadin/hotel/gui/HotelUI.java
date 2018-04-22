package com.gp.vaadin.hotel.gui;

import javax.servlet.annotation.WebServlet;

import com.gp.vaadin.hotel.view.CategoryView;
import com.gp.vaadin.hotel.view.HotelView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class HotelUI extends UI {
	
	final VerticalLayout layout = new VerticalLayout();
	final VerticalLayout content = new VerticalLayout();
	final MenuBar menu = new MenuBar();
	private Navigator navigator;
	protected static final String HOTEL_VIEW = "hotels";
	protected static final String CATEGORY_VIEW = "categorys";
	
	@SuppressWarnings("serial")
    @Override
    protected void init(VaadinRequest vaadinRequest) {
		
		navigator = new Navigator(this,content);
		
		navigator.addView(CATEGORY_VIEW, new CategoryView());
		navigator.addView(HOTEL_VIEW, new HotelView());
		navigator.navigateTo(HOTEL_VIEW);
		
    	setContent(layout);

    	//menu
    	MenuBar.Command cmdHotel = new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo(HOTEL_VIEW);
			}
		};
    	MenuBar.Command cmdCategory = new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				navigator.navigateTo(CATEGORY_VIEW);
			}
		};
    
    	MenuItem hotelItem = menu.addItem("Hotel", VaadinIcons.BUILDING, cmdHotel);
    	MenuItem categoryItem = menu.addItem("Category", VaadinIcons.ACADEMY_CAP, cmdCategory);
    	menu.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
    	
    	layout.addComponents(menu,content);
    	content.setMargin(false);
    	layout.setMargin(false);

    }

	@WebServlet(urlPatterns = "/*", name = "HotelUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = HotelUI.class, productionMode = false)
    public static class HotelUIServlet extends VaadinServlet {
    }
}
