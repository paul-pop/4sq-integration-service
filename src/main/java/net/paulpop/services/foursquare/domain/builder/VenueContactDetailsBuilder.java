package net.paulpop.services.foursquare.domain.builder;

import net.paulpop.services.foursquare.domain.VenueContactDetails;

/**
 * Builder for {@link VenueContactDetails}
 */
public class VenueContactDetailsBuilder {

    private String phoneNumber;
    private String twitterHandle;

    public VenueContactDetailsBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public VenueContactDetailsBuilder withTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
        return this;
    }

    public VenueContactDetails build() {
        VenueContactDetails venueContactDetails = new VenueContactDetails();
        venueContactDetails.setPhoneNumber(phoneNumber);
        venueContactDetails.setTwitterHandle(twitterHandle);
        return venueContactDetails;
    }
}