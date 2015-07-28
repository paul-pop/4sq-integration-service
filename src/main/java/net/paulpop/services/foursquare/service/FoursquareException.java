package net.paulpop.services.foursquare.service;

/**
 * Custom Foursquare exception that contains error information.
 *
 * Created by popp on 28/07/15.
 */
public class FoursquareException extends Exception {

    private int responseCode;
    private String errorCode;
    private String errorDetail;

    public FoursquareException(String message, Throwable cause) {
        super(message, cause);
    }

    public FoursquareException(int responseCode, String errorCode, String errorDetail) {
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

        if (responseCode != that.responseCode) return false;
        if (errorDetail != null ? !errorDetail.equals(that.errorDetail) : that.errorDetail != null) return false;
        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = responseCode;
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        result = 31 * result + (errorDetail != null ? errorDetail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FoursquareException{" +
                "responseCode=" + responseCode +
                ", errorCode='" + errorCode + '\'' +
                ", errorDetail='" + errorDetail + '\'' +
                '}';
    }
}
