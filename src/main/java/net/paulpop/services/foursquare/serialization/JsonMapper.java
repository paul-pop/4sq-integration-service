package net.paulpop.services.foursquare.serialization;

import net.paulpop.services.foursquare.exception.FoursquareException;

/**
 * Mapper generic interface, used for mapping an already deserialized objects into a domain model
 *
 * Created by popp on 28/07/15.
 */
public interface JsonMapper<V, T> {

    T map(V from) throws FoursquareException;
}
