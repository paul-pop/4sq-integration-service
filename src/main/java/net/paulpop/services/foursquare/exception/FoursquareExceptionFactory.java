package net.paulpop.services.foursquare.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory created once that is used to create {@link FoursquareException}s
 *
 * Created by popp on 28/07/15.
 */
public class FoursquareExceptionFactory {

    private static final Logger logger = LoggerFactory.getLogger(FoursquareExceptionFactory.class);
    public static final String GENERIC_ERROR = "UNEXPECTED_ERROR";

    private static volatile FoursquareExceptionFactory instance;

    private FoursquareExceptionFactory() {}

    /**
     * Synchronized singleton to get only one instance of the factory.
     *
     * @return
     */
    public static FoursquareExceptionFactory getInstance() {
        if (instance == null) {
            synchronized (FoursquareExceptionFactory.class) {
                if (instance == null) {
                    instance = new FoursquareExceptionFactory();
                }
            }
        }
        return instance;
    }

    public FoursquareException create(int responseCode, String errorCode, String errorDetail) {
        return new FoursquareException(responseCode, errorCode, errorDetail);
    }
}
