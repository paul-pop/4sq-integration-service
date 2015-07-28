package net.paulpop.services.foursquare.service;

import com.google.gson.JsonObject;
import net.paulpop.services.foursquare.client.JettyFoursquareClient;
import net.paulpop.services.foursquare.domain.Venue;
import net.paulpop.services.foursquare.domain.VenuesResponse;
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
    private JsonSerDeser<VenuesResponse> jsonSerDeser;

    @Autowired
    @Qualifier("venueJsonMapper")
    private JsonMapper<JsonObject, List<Venue>> jsonMapper;

    @Override
    public VenuesResponse explore(String near, Integer radius, Integer limit) throws FoursquareException {
        // We use partial deserialization so we don't have to create object in our codebase for
        // the entire Foursquare model
        String result = client.call(FoursquareOperation.EXPLORE, near, radius, limit);
        JsonObject json = jsonSerDeser.deserialize(result).getAsJsonObject();

        // Get the meta definition which contains response/error code information
        JsonObject status = json.get("meta").getAsJsonObject();
        if (status.get("code").getAsInt() != 200) { // invalid request
            throw new FoursquareException(status.get("code").getAsInt(),
                    status.get("errorType").getAsString().toUpperCase(),
                    status.get("errorDetail").getAsString());
        }

        // This means the response was OK and deserialization passed so we return 200
        VenuesResponse response = new VenuesResponse();
        response.setResponseCode(200);
        response.setVenues(jsonMapper.map(json));
        return response;
    }

}
