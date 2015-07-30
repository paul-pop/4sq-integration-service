package net.paulpop.services.foursquare.domain;

/**
 * Created by popp on 28/07/15.
 */
public class VenueContactDetails {

    private String phone;
    private String website;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VenueContactDetails)) return false;

        VenueContactDetails that = (VenueContactDetails) o;

        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (website != null ? !website.equals(that.website) : that.website != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = phone != null ? phone.hashCode() : 0;
        result = 31 * result + (website != null ? website.hashCode() : 0);
        return result;
    }
}
