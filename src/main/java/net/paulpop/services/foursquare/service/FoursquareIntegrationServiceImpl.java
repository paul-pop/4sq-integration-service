package net.paulpop.services.foursquare.service;

import net.paulpop.services.foursquare.client.JettyFoursquareClient;
import net.paulpop.services.foursquare.domain.VenuesResponse;
import net.paulpop.services.foursquare.util.FoursquareOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Sync implementation of the {@link FoursquareIntegrationService} interface.
 * Only the required method will be implemented which in this case is {@link #explore}
 *
 * Created by popp on 28/07/15.
 */
@Component
public class FoursquareIntegrationServiceImpl implements FoursquareIntegrationService {

    @Autowired
    private JettyFoursquareClient client;

    @Override
    public VenuesResponse explore(String near, Integer radius, Integer limit) throws FoursquareException {
        final String result = client.call(FoursquareOperation.EXPLORE, near, radius, limit);

        // TODO deserialize this into a VenuesResponse
        VenuesResponse response = new VenuesResponse();
        return response;
    }

}
