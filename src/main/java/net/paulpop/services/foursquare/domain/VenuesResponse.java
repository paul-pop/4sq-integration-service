package net.paulpop.services.foursquare.domain;

import java.util.List;

/**
 * Wrapper around a list of venues returned in the response that also returns HTTP response codes/errors.
 *
 * Created by popp on 28/07/15.
 */
public class VenuesResponse {

    // Mandatory fields
    private int responseCode;
    private List<Venue> venues;

    // Optional fields - only in case of error
    private String errorCode;
    private String errorDetail;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VenuesResponse)) return false;

        VenuesResponse that = (VenuesResponse) o;

        if (responseCode != that.responseCode) return false;
        if (errorDetail != null ? !errorDetail.equals(that.errorDetail) : that.errorDetail != null) return false;
        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) return false;
        if (venues != null ? !venues.equals(that.venues) : that.venues != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = responseCode;
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        result = 31 * result + (errorDetail != null ? errorDetail.hashCode() : 0);
        result = 31 * result + (venues != null ? venues.hashCode() : 0);
        return result;
    }
}
