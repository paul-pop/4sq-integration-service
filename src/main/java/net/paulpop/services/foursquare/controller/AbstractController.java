package net.paulpop.services.foursquare.controller;

import net.paulpop.services.foursquare.exception.FoursquareException;
import net.paulpop.services.foursquare.serialization.JsonSerDeser;
import net.paulpop.services.foursquare.service.FoursquareIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract controller that uses generics in order to enforce proper serialization.
 *
 * Created by popp on 28/07/15.
 */
abstract class AbstractController<T> {

    // No qualifier needed now but in case we have a different impl, we just update here
    //@Qualifier("foursquareIntegrationServiceImpl")
    @Autowired
    protected FoursquareIntegrationService service;

    @Autowired
    protected JsonSerDeser<T> jsonSerDeser;

    protected String serialize(T source) {
        return jsonSerDeser.serialize(source);
    }

    protected abstract T generateErrorResponse(FoursquareException ex);

}
