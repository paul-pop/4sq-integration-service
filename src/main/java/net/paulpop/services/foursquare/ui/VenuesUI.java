package net.paulpop.services.foursquare.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import net.paulpop.services.foursquare.domain.Venue;
import net.paulpop.services.foursquare.domain.VenueAddress;
import net.paulpop.services.foursquare.domain.VenueContactDetails;
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

    // default values, needs to be atomic if multiple users do search at the same time
    private AtomicInteger limit = new AtomicInteger(20);
    private AtomicInteger radius = new AtomicInteger(100);

    @Autowired
    private FoursquareIntegrationService service;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        // Create main page layout
        VerticalLayout vl = new VerticalLayout();
        HorizontalLayout hl1 = new HorizontalLayout();
        HorizontalLayout hl2 = new HorizontalLayout();

        hl1.setMargin(false);
        hl2.setMargin(false);

        // Create the field for place and add validator
        TextField tf = new TextField("Place");
        tf.addStyleName(ValoTheme.TEXTFIELD_TINY);
        tf.setIcon(FontAwesome.MAP_MARKER);
        tf.setRequired(true);
        tf.setInputPrompt("e.g. London");
        tf.addValidator(new StringLengthValidator("The place must have at least 2 characters", 2, 1000, false));
        tf.setImmediate(true);

        // Create the field for radius
        ComboBox cb1 = new ComboBox("Radius (in meters)", radiusValues);
        cb1.addStyleName(ValoTheme.COMBOBOX_TINY);
        cb1.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
        cb1.setIcon(FontAwesome.COMPASS);
        cb1.setWidth(90, Unit.PIXELS);
        cb1.setValue(radius.get());
        cb1.setImmediate(true);
        cb1.addValueChangeListener(event -> radius.set((Integer) event.getProperty().getValue()));

        // Create the dropdown for limit with predefined values
        ComboBox cb2 = new ComboBox("Result limit", limitValues);
        cb2.addStyleName(ValoTheme.COMBOBOX_TINY);
        cb2.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
        cb2.setIcon(FontAwesome.LIST);
        cb2.setWidth(90, Unit.PIXELS);
        cb2.setValue(limit.get());
        cb2.setImmediate(true);
        cb2.addValueChangeListener(event -> limit.set((Integer) event.getProperty().getValue()));

        // Create table, set headers and table properties
        Table t = new Table("Places nearby", null);
        t.addStyleName(ValoTheme.TABLE_COMPACT);
        t.setSizeFull();
        t.setVisible(false);
        t.addContainerProperty("ID", String.class, null);
        t.addContainerProperty("Name", String.class, null);
        t.addContainerProperty("Category", String.class, null);
        t.addContainerProperty("Contact", VenueContactDetails.class, null);
        t.addContainerProperty("Address", VenueAddress.class, null);
        t.addContainerProperty("Open?", String.class, null);
        t.addContainerProperty("Short URL", String.class, null);

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

                // If everything is ok, make the table visibile, purge previous data and display new data
                try {
                    VenuesResponse venuesResponse = service.explore(tf.getValue(),
                            Integer.valueOf(cb1.getValue().toString()),
                            Integer.valueOf(cb2.getValue().toString()));
                    if (venuesResponse != null && venuesResponse.getVenues() != null && venuesResponse.getVenues().size() != 0) {
                        t.removeAllItems();
                        t.setVisible(true);

                        List<Venue> venues = venuesResponse.getVenues();
                        venues.forEach(venue -> {
                            t.addItem(new Object[]{venue.getId(), venue.getName(), venue.getCategory(),
                                    venue.getContactDetails(), venue.getAddress(),
                                    venue.getIsOpen() ? "Yes" : "No", venue.getUrl()}, null);
                        });
                    } else {
                        // If no venues were found, don't display the table and show a warning
                        t.setVisible(false);
                        Notification.show("No venues found, try expanding your search!", Notification.Type.WARNING_MESSAGE);
                    }
                } catch (FoursquareException e) {
                    Notification.show("An error occured when trying to retrieve the data", Notification.Type.ERROR_MESSAGE);
                }
            } catch (Validator.InvalidValueException e) {
                Notification.show("Values are invalid, please re-check.", Notification.Type.ERROR_MESSAGE);
            }
        });

        hl1.addComponent(new FormLayout(tf, cb1, cb2, btn));
        hl2.addComponent(t);

        vl.addComponents(getTitle(), hl1, hl2);
        setContent(vl);
    }

    private Label getTitle() {
        Label l = new Label();
        l.setContentMode(ContentMode.HTML);
        l.setStyleName(ValoTheme.LABEL_H3);
        l.setValue(FontAwesome.FOURSQUARE.getHtml() + " AND 4sq integration");
        return l;
    }

}
