package net.paulpop.services.foursquare.util;

/**
 * Utility enum that maps to a context path used in the Foursquare API.
 *
 * Created by popp on 28/07/15.
 */
public enum FoursquareOperation {

    EXPLORE("/venues/explore"),
    SEARCH("/venues/search");

    private final String path;

    private FoursquareOperation(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
