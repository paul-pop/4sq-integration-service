package net.paulpop.services.foursquare.domain.builder;

import net.paulpop.services.foursquare.domain.VenueAddress;

/**
 * Builder for {@link net.paulpop.services.foursquare.domain.VenueAddress}
 */
public class VenueAddressBuilder {

    private String country;
    private String city;
    private String street;

    public VenueAddressBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public VenueAddressBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public VenueAddressBuilder withStreet(String street) {
        this.street = street;
        return this;
    }

    public VenueAddress build() {
        VenueAddress venueAddress = new VenueAddress();
        venueAddress.setCountry(country);
        venueAddress.setCity(city);
        venueAddress.setStreet(street);
        return venueAddress;
    }
}