package net.paulpop.services.foursquare.service;

import net.paulpop.services.foursquare.domain.VenuesResponse;
import net.paulpop.services.foursquare.exception.FoursquareException;

/**
 * This interface defines all the operations called on the Foursquare API by our service.
 * It can be implemented in multiple ways and wired differently via Spring based on requirements.
 * (e.g. Asynchronous implementation, Synchronous implementation, calling the Foursquare API via a
 * HTTP client or via the Foursquare API client library)
 *
 * <b>Note</b>: For the sake of this exercise, I will only do one synchronous implementation and
 * will use a Jetty client to perform the API call.
 *
 * Created by popp on 28/07/15.
 */
public interface FoursquareIntegrationService {

    VenuesResponse explore(String near, Integer radius, Integer limit) throws FoursquareException;

}
