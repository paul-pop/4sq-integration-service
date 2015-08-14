package net.paulpop.services.foursquare.domain;

import java.util.Objects;

/**
 * Created by popp on 28/07/15.
 */
public class Venue {

    private String id;
    private String name;
    private String category;
    private VenueContactDetails contactDetails;
    private VenueAddress address;
    private boolean isOpen;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public VenueContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(VenueContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public VenueAddress getAddress() {
        return address;
    }

    public void setAddress(VenueAddress address) {
        this.address = address;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venue)) return false;
        Venue that = (Venue) o;

        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(category, that.category) &&
                Objects.equals(contactDetails, that.contactDetails) &&
                Objects.equals(address, that.address) &&
                Objects.equals(isOpen, that.isOpen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, contactDetails, address, isOpen);
    }
}
