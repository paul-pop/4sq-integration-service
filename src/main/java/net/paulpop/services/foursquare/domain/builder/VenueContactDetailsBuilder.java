package net.paulpop.services.foursquare.domain.builder;

import net.paulpop.services.foursquare.domain.VenueContactDetails;

/**
 * Builder for {@link VenueContactDetails}
 */
public class VenueContactDetailsBuilder {

    private String phone;
    private String website;

    public VenueContactDetailsBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public VenueContactDetailsBuilder withWebsite(String website) {
        this.website = website;
        return this;
    }

    public VenueContactDetails build() {
        VenueContactDetails venueContactDetails = new VenueContactDetails();
        venueContactDetails.setPhone(phone);
        venueContactDetails.setWebsite(website);
        return venueContactDetails;
    }
}