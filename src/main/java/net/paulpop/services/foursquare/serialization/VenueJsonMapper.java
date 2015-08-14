package net.paulpop.services.foursquare.serialization;

import com.google.common.collect.Lists;
import net.paulpop.services.foursquare.domain.Venue;
import net.paulpop.services.foursquare.domain.builder.VenueAddressBuilder;
import net.paulpop.services.foursquare.domain.builder.VenueBuilder;
import net.paulpop.services.foursquare.domain.builder.VenueContactDetailsBuilder;
import net.paulpop.services.foursquare.domain.external.FoursquareItem;
import net.paulpop.services.foursquare.domain.external.FoursquareRoot;
import net.paulpop.services.foursquare.domain.external.FoursquareVenue;
import net.paulpop.services.foursquare.exception.FoursquareException;
import net.paulpop.services.foursquare.exception.FoursquareExceptionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static net.paulpop.services.foursquare.util.ValidationUtil.nvl;

/**
 * Specific mapper used for deserializing Foursquare API venue information.
 *
 * Created by popp on 28/07/15.
 */
@Component
final class VenueJsonMapper implements JsonMapper<FoursquareRoot.FoursquareResponse, List<Venue>> {

    private static final String EMPTY_STRING = "";

    /**
     * Map the initial {@link FoursquareRoot.FoursquareResponse} obtained after deserialization to our current model
     *
     * @param from
     * @return
     * @throws FoursquareException
     */
    @Override
    public List<Venue> map(FoursquareRoot.FoursquareResponse from) throws FoursquareException {
        try {
            List<Venue> venues = Lists.newArrayList();
            List<FoursquareItem> items = from.getGroups().get(0).getItems();

            items.forEach(element -> {
                FoursquareVenue venue = element.getVenue();
                venues.add(new VenueBuilder()
                        .withId(venue.getId())
                        .withName(venue.getName())
                        .withIsOpen(venue.getHours() != null ? nvl(venue.getHours().isOpen(), false) : false)
                        .withCategory(nvl(venue.getCategories().get(0).getName(), EMPTY_STRING))
                        .withContactDetails(new VenueContactDetailsBuilder()
                                .withPhone(nvl(venue.getContact().getFormattedPhone(), EMPTY_STRING))
                                .withWebsite(nvl(venue.getUrl(), EMPTY_STRING))
                                .build())
                        .withAddress(new VenueAddressBuilder()
                                .withStreet(nvl(venue.getLocation().getAddress(), EMPTY_STRING))
                                .withCity(nvl(venue.getLocation().getCity(), EMPTY_STRING))
                                .withCountry(nvl(venue.getLocation().getCountry(), EMPTY_STRING))
                                .build())
                        .build());
            });

            return venues;
        } catch (Exception e) {
            throw FoursquareExceptionFactory.getInstance().create("Exception occurred when mapping data from Foursquare API", e);
        }
    }

}
