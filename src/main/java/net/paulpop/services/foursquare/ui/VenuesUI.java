package net.paulpop.services.foursquare.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import net.paulpop.services.foursquare.domain.Venue;
import net.paulpop.services.foursquare.domain.VenuesResponse;
import net.paulpop.services.foursquare.exception.FoursquareException;
import net.paulpop.services.foursquare.service.FoursquareIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UI definition for the Venues page.
 *
 * Created by popp on 28/07/15.
 */
@Theme("valo")
@Title("AND 4sq")
@SpringUI(path = "/venues/search")
final class VenuesUI extends UI {

    @Value("#{'${ui.limit.values}'.split(',')}")
    private List<Integer> limitValues;

    @Value("#{'${ui.radius.values}'.split(',')}")
    private List<Integer> radiusValues;

    @Value("${ui.limit.default}")
    private int limitDefault;

    @Value("${ui.radius.default}")
    private int radiusDefault;

    // default values, needs to be atomic if multiple users do search at the same time

    @Autowired
    private FoursquareIntegrationService service;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        AtomicInteger limit = new AtomicInteger(limitDefault);
        AtomicInteger radius = new AtomicInteger(radiusDefault);

        // Create the field for place and add validator
        TextField tf = new TextField("Place");
        tf.addStyleName(ValoTheme.TEXTFIELD_TINY);
        tf.setIcon(FontAwesome.MAP_MARKER);
        tf.setRequired(true);
        tf.setInputPrompt("e.g. London");
        tf.addValidator(new StringLengthValidator("The place must have at least 2 characters", 2, 1000, false));
        tf.setImmediate(true);

        // Create the dropdown for radius with predefined values
        ComboBox cb1 = new ComboBox("Radius (in meters)", radiusValues);
        cb1.addStyleName(ValoTheme.COMBOBOX_TINY);
        cb1.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
        cb1.setIcon(FontAwesome.COMPASS);
        cb1.setWidth(90, Unit.PIXELS);
        cb1.setValue(radius.get());
        cb1.setImmediate(true);
        cb1.setNullSelectionAllowed(false);
        cb1.addValueChangeListener(event -> radius.set((Integer) event.getProperty().getValue()));

        // Create the dropdown for limit with predefined values
        ComboBox cb2 = new ComboBox("Result limit", limitValues);
        cb2.addStyleName(ValoTheme.COMBOBOX_TINY);
        cb2.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
        cb2.setIcon(FontAwesome.LIST);
        cb2.setWidth(90, Unit.PIXELS);
        cb2.setValue(limit.get());
        cb2.setImmediate(true);
        cb2.setNullSelectionAllowed(false);
        cb2.addValueChangeListener(event -> limit.set((Integer) event.getProperty().getValue()));

        // Create table, set headers and table properties
        Table t = new Table("Nearby places", null);
        t.addStyleName(ValoTheme.TABLE_SMALL);
        t.setSizeFull();
        t.setVisible(false);
        t.setSelectable(true);
        t.addContainerProperty("Name", String.class, null);
        t.addContainerProperty("Category", String.class, null);
        t.addContainerProperty("Open now?", String.class, null);
        t.addContainerProperty("Phone Number", String.class, null);
        t.addContainerProperty("Website", String.class, null);
        t.addContainerProperty("Address", String.class, null);
        t.addContainerProperty("City", String.class, null);
        t.addContainerProperty("Country", String.class, null);
        t.setColumnExpandRatio(null, 1);

        // Create search button and attach a click action to it
        Button btn = new Button("Search");
        btn.addStyleName(ValoTheme.BUTTON_SMALL);
        btn.setIcon(FontAwesome.SEARCH);

        btn.addClickListener(event -> {
            try {
                // Validate all fields before submitting
                tf.validate();
                cb1.validate();
                cb2.validate();

                // If everything is ok, make the table visible, purge previous data and display new data
                try {
                    VenuesResponse venuesResponse = service.explore(tf.getValue(),
                            Integer.valueOf(cb1.getValue().toString()),
                            Integer.valueOf(cb2.getValue().toString()));
                    if (venuesResponse != null && venuesResponse.getVenues() != null && venuesResponse.getVenues().size() != 0) {
                        t.removeAllItems();
                        t.setVisible(true);

                        List<Venue> venues = venuesResponse.getVenues();
                        venues.stream().forEach(venue -> t.addItem(new Object[]{
                                venue.getName(), venue.getCategory(), venue.getIsOpen() ? "Yes" : "No",
                                venue.getContactDetails().getPhone(), venue.getContactDetails().getWebsite(),
                                venue.getAddress().getStreet(), venue.getAddress().getCity(), venue.getAddress().getCountry()
                        }, null));
                    } else {
                        // If no venues were found, don't display the table and show a warning
                        t.setVisible(false);
                        Notification.show("No venues found, try expanding your search!", Notification.Type.WARNING_MESSAGE);
                    }
                } catch (FoursquareException e) {
                    Notification.show("An error occurred when trying to retrieve the data", Notification.Type.ERROR_MESSAGE);
                }
            } catch (Validator.InvalidValueException e) {
                Notification.show("Values are invalid, please re-check.", Notification.Type.ERROR_MESSAGE);
            }
        });

        // Add a shortcut to the main text field, when you press ENTER the search is triggered
        tf.addShortcutListener(new Button.ClickShortcut(btn, ShortcutAction.KeyCode.ENTER, null));


        // Create main page layout
        HorizontalLayout hl1 = new HorizontalLayout();
        hl1.addComponent(new FormLayout(tf, cb1, cb2, btn));
        HorizontalLayout hl2 = new HorizontalLayout();
        hl2.addComponent(t);
        hl2.setSizeFull();

        VerticalLayout vl = new VerticalLayout();
        vl.addComponents(hl1, hl2);
        setContent(vl);
    }

}
