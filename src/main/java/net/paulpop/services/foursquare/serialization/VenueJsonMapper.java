package net.paulpop.services.foursquare.serialization;

import com.google.gson.JsonObject;
import net.paulpop.services.foursquare.domain.Venue;
import net.paulpop.services.foursquare.service.FoursquareException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Specific mapper used for deserializing Foursquare API venue information.
 *
 * Created by popp on 28/07/15.
 */
@Component
public class VenueJsonMapper implements JsonMapper<JsonObject, List<Venue>> {

    @Override
    public List<Venue> map(JsonObject from) throws FoursquareException {
        return null;
    }
}
