package net.paulpop.services.foursquare.domain;

/**
 * Created by popp on 28/07/15.
 */
public class Venue {

    private String id;
    private String name;
    private String category;
    private boolean isOpen;
    private VenueContactDetails contactDetails;
    private VenueAddress address;

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

        Venue venue = (Venue) o;

        if (isOpen != venue.isOpen) return false;
        if (address != null ? !address.equals(venue.address) : venue.address != null) return false;
        if (category != null ? !category.equals(venue.category) : venue.category != null) return false;
        if (contactDetails != null ? !contactDetails.equals(venue.contactDetails) : venue.contactDetails != null)
            return false;
        if (id != null ? !id.equals(venue.id) : venue.id != null) return false;
        if (name != null ? !name.equals(venue.name) : venue.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (contactDetails != null ? contactDetails.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (isOpen ? 1 : 0);
        return result;
    }
}
