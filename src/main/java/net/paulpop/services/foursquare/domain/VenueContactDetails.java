package net.paulpop.services.foursquare.domain;

/**
 * Created by popp on 28/07/15.
 */
public class VenueContactDetails {

    private String phoneNumber;
    private String twitterHandle;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VenueContactDetails)) return false;

        VenueContactDetails that = (VenueContactDetails) o;

        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (twitterHandle != null ? !twitterHandle.equals(that.twitterHandle) : that.twitterHandle != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = phoneNumber != null ? phoneNumber.hashCode() : 0;
        result = 31 * result + (twitterHandle != null ? twitterHandle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Phone Number: " + phoneNumber + " | " +
                "Twitter: @" + twitterHandle;
    }
}
