package net.paulpop.services.foursquare.domain;

import java.util.Objects;

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

        return Objects.equals(phone, that.phone) &&
                Objects.equals(website, that.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, website);
    }
}
