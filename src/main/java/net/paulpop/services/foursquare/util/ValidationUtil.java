package net.paulpop.services.foursquare.util;

/**
 * Utility class used for simple validation.
 *
 * Created by popp on 28/07/15.
 */
public final class ValidationUtil {

    public static final String EMPTY_STRING = "";

    // Should not be instantiated as it will only contain static methods - this is to avoid invalid invocations
    private ValidationUtil() {}

    /**
     * Validate the provided value to be non-null
     * @param a
     * @return T
     */
    public static <T> T mandatory(T a) {
        if (a == null) {
            throw new IllegalArgumentException("This parameter is required!");
        } else {
            return a;
        }
    }

    /**
     * Nvl function that takes in a given value and a default which is returned if the value is null
     * @param a
     * @param b
     * @return T
     */
    public static <T> T nvl(T a, T b) {
        return a == null ? b : a;
    }

}
