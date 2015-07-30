package net.paulpop.services.foursquare.controller;

import net.paulpop.services.foursquare.domain.VenuesResponse;
import net.paulpop.services.foursquare.exception.FoursquareException;
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

    @RequestMapping(method = RequestMethod.GET, value = "/search/rest", produces = "application/json")
    public final String searchVenues(@RequestParam("place") String place,
                          @RequestParam("radius") Integer radius,
                          @RequestParam("limit") Integer limit) {

        VenuesResponse response;
        try {
            response = service.explore(place, radius, limit);
        } catch (FoursquareException e) {
            System.err.println(e.toString()); // seeing we don't have logging, we'll just stderr it
            response = generateErrorResponse(e);
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
