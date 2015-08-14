package net.paulpop.services.foursquare.exception;

import java.util.Objects;

/**
 * Custom Foursquare exception that contains error information.
 *
 * Created by popp on 28/07/15.
 */
public class FoursquareException extends Exception {

    private int responseCode;
    private String errorCode;
    private String errorDetail;

    // default modifier because only the factory can instantiate new exceptions
    FoursquareException(String message, Throwable cause) {
        super("FoursquareException thrown with: message=" + message, cause);
    }

    // default modifier because only the factory can instantiate new exceptions
    FoursquareException(int responseCode, String errorCode, String errorDetail) {
        super("FoursquareException thrown with: responseCode=" + responseCode + ",errorCode=" + errorCode + ",errorDetail=" + errorDetail);

        this.responseCode = responseCode;
        this.errorCode = errorCode;
        this.errorDetail = errorDetail;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoursquareException)) return false;
        FoursquareException that = (FoursquareException) o;

        return Objects.equals(responseCode, that.responseCode) &&
                Objects.equals(errorCode, that.errorCode) &&
                Objects.equals(errorDetail, that.errorDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseCode, errorCode, errorDetail);
    }
}
