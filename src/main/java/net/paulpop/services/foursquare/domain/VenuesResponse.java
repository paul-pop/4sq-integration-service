package net.paulpop.services.foursquare.domain;

import java.util.List;
import java.util.Objects;

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

        return Objects.equals(responseCode, that.responseCode) &&
                Objects.equals(venues, that.venues) &&
                Objects.equals(errorCode, that.errorCode) &&
                Objects.equals(errorDetail, that.errorDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseCode, venues, errorCode, errorDetail);
    }

    @Override
    public String toString() {
        return "VenuesResponse{" +
                "responseCode=" + responseCode +
                ", venues=" + venues +
                ", errorCode='" + errorCode + '\'' +
                ", errorDetail='" + errorDetail + '\'' +
                '}';
    }
}
