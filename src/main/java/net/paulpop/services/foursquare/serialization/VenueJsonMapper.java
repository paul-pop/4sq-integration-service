package net.paulpop.services.foursquare.serialization;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.paulpop.services.foursquare.domain.Venue;
import net.paulpop.services.foursquare.domain.builder.VenueAddressBuilder;
import net.paulpop.services.foursquare.domain.builder.VenueBuilder;
import net.paulpop.services.foursquare.domain.builder.VenueContactDetailsBuilder;
import net.paulpop.services.foursquare.exception.FoursquareException;
import net.paulpop.services.foursquare.exception.FoursquareExceptionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static net.paulpop.services.foursquare.util.ValidationUtil.EMPTY_STRING;

/**
 * Specific mapper used for deserializing Foursquare API venue information.
 *
 * Created by popp on 28/07/15.
 */
@Component
final class VenueJsonMapper implements JsonMapper<JsonObject, List<Venue>> {

    /**
     * Map the initial {@link JsonObject} obtained after deserialization to our current model
     *
     * @param from
     * @return
     * @throws FoursquareException
     */
    @Override
    public List<Venue> map(JsonObject from) throws FoursquareException {
        try {
            List<Venue> venues = Lists.newArrayList();

            JsonArray itemsList = from.get("response").getAsJsonObject().get("groups").getAsJsonArray().get(0).getAsJsonObject().get("items").getAsJsonArray();
            itemsList.forEach(element -> {
                JsonObject venue = element.getAsJsonObject().get("venue").getAsJsonObject();
                JsonObject category = venue.get("categories").getAsJsonArray().get(0).getAsJsonObject();
                JsonObject contact = venue.get("contact").getAsJsonObject();
                JsonObject location = venue.get("location").getAsJsonObject();

                venues.add(new VenueBuilder()
                        .withId(venue.get("id").getAsString())
                        .withName(venue.get("name").getAsString())
                        .withUrl(validate(venue.get("url")))
                        .withIsOpen(venue.get("hours") != null ? venue.get("hours").getAsJsonObject().get("isOpen").getAsBoolean() : false)
                        .withCategory(validate(category.get("name")))
                        .withContactDetails(new VenueContactDetailsBuilder()
                                .withPhoneNumber(validate(contact.get("formattedPhone")))
                                .withTwitterHandle(validate(contact.get("twitter")))
                                .build())
                        .withAddress(new VenueAddressBuilder()
                                .withAddress(validate(location.get("address")))
                                .withCity(validate(location.get("city")))
                                .withCountry(validate(location.get("country")))
                                .build())
                        .build());
            });

            return venues;
        } catch (Exception e) {
            throw FoursquareExceptionFactory.getInstance().create("Exception occurred when mapping data from Foursquare API", e);
        }
    }

    private static String validate(JsonElement jsonElement) {
        return jsonElement == null ? EMPTY_STRING : jsonElement.getAsString();
    }
}
