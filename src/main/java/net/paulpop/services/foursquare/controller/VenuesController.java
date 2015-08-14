package net.paulpop.services.foursquare.controller;

import net.paulpop.services.foursquare.domain.VenuesResponse;
import net.paulpop.services.foursquare.exception.FoursquareException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that produces a JSON structure mapped to our internal model so it can be used as an API.
 * The web interface can then use the REST API to build the UI elements but seeing they are in the same place
 * we will be calling the client directly.
 *
 * Created by popp on 28/07/15.
 */
@RestController
@RequestMapping("/venues")
public class VenuesController extends AbstractController<VenuesResponse> {

    private static final Logger logger = LoggerFactory.getLogger(VenuesController.class);

    // We set the fields to required = false because we don't want Spring MVC to deal with empty params.
    // We deal with them ourselves
    @RequestMapping(method = RequestMethod.GET, value = "/search/rest", produces = "application/json")
    public final String searchVenues(@RequestParam(value = "place", required = false) String place,
                          @RequestParam(value = "radius", required = false) Integer radius,
                          @RequestParam(value = "limit", required = false) Integer limit) {

        VenuesResponse response;
        try {
            response = service.explore(place, radius, limit);
        } catch (FoursquareException e) {
            response = generateErrorResponse(e);
            logger.warn(response.toString());
        }

        return serialize(response);
    }

    @Override
    protected VenuesResponse generateErrorResponse(FoursquareException e) {
        VenuesResponse response = new VenuesResponse();
        response.setResponseCode(e.getResponseCode());
        response.setErrorCode(e.getErrorCode());
        response.setErrorDetail(e.getErrorDetail());
        return response;
    }

}
