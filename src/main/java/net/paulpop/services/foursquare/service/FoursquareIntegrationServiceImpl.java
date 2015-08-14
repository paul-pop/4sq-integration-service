package net.paulpop.services.foursquare.service;

import net.paulpop.services.foursquare.client.JettyFoursquareClient;
import net.paulpop.services.foursquare.domain.Venue;
import net.paulpop.services.foursquare.domain.VenuesResponse;
import net.paulpop.services.foursquare.domain.external.FoursquareRoot;
import net.paulpop.services.foursquare.exception.FoursquareException;
import net.paulpop.services.foursquare.exception.FoursquareExceptionFactory;
import net.paulpop.services.foursquare.serialization.JsonMapper;
import net.paulpop.services.foursquare.serialization.JsonSerDeser;
import net.paulpop.services.foursquare.util.FoursquareOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Sync implementation of the {@link FoursquareIntegrationService} interface.
 * Only the required method will be implemented which in this case is {@link #explore}
 *
 * Created by popp on 28/07/15.
 */
@Component
final class FoursquareIntegrationServiceImpl implements FoursquareIntegrationService {

    @Autowired
    private JettyFoursquareClient client;

    @Autowired
    private JsonSerDeser<FoursquareRoot> jsonSerDeser;

    @Autowired
    @Qualifier("venueJsonMapper")
    private JsonMapper<FoursquareRoot.FoursquareResponse, List<Venue>> jsonMapper;

    @Override
    public VenuesResponse explore(String near, Integer radius, Integer limit) throws FoursquareException {
        String json = client.call(FoursquareOperation.EXPLORE, near, radius, limit);

        // We use partial deserialization so we don't have to create objects in our codebase for Foursquare model
        // This is something provided internally by Gson
        FoursquareRoot deserializedResult = jsonSerDeser.deserialize(json, FoursquareRoot.class);

        // Get the meta definition which contains response/error code information
        FoursquareRoot.FoursquareMeta metadata = deserializedResult.getMeta();
        if (metadata.getCode() != 200) { // invalid request
            throw FoursquareExceptionFactory.getInstance().create(
                    metadata.getCode(),
                    metadata.getErrorType().toUpperCase(),
                    metadata.getErrorDetail());
        }

        List<Venue> venues = jsonMapper.map(deserializedResult.getResponse());
        VenuesResponse response = new VenuesResponse();
        response.setResponseCode(200);
        response.setVenues(venues);
        return response;

    }

}
