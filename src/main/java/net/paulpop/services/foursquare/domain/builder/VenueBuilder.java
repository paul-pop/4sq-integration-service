package net.paulpop.services.foursquare.domain.builder;

import net.paulpop.services.foursquare.domain.Venue;
import net.paulpop.services.foursquare.domain.VenueAddress;
import net.paulpop.services.foursquare.domain.VenueContactDetails;

/**
 * Builder for {@link net.paulpop.services.foursquare.domain.Venue}
 */
public class VenueBuilder {

    private String id;
    private String name;
    private String category;
    private VenueContactDetails contactDetails;
    private VenueAddress address;
    private boolean isOpen;
    private String url;

    public VenueBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public VenueBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public VenueBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    public VenueBuilder withContactDetails(VenueContactDetails contactDetails) {
        this.contactDetails = contactDetails;
        return this;
    }

    public VenueBuilder withAddress(VenueAddress address) {
        this.address = address;
        return this;
    }

    public VenueBuilder withIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
        return this;
    }

    public VenueBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public Venue build() {
        Venue venue = new Venue();
        venue.setId(id);
        venue.setName(name);
        venue.setCategory(category);
        venue.setContactDetails(contactDetails);
        venue.setAddress(address);
        venue.setIsOpen(isOpen);
        venue.setUrl(url);
        return venue;
    }
}