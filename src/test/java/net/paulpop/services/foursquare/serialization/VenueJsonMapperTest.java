package net.paulpop.services.foursquare.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.paulpop.services.foursquare.domain.Venue;
import net.paulpop.services.foursquare.exception.FoursquareException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

/**
 * Created by popp on 29/07/15.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonObject.class, JsonElement.class, JsonArray.class, FoursquareException.class})
public class VenueJsonMapperTest extends PowerMockTestCase {

    @Mock private JsonObject jsonObject;
    @Mock private JsonElement jsonElement;
    @Mock private JsonArray jsonArray;

    @InjectMocks
    private VenueJsonMapper mapper;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
    }

    @Test
    public void testMapper_ThrowsException() {
        when(jsonObject.get("response")).thenThrow(new RuntimeException());

        try {
            mapper.map(jsonObject);
        } catch (FoursquareException e) {
            assertTrue(e.getCause() instanceof RuntimeException);
            assertTrue(e.getMessage().startsWith("Exception occurred when mapping data from Foursquare API"));
        } catch (Exception e) {
            fail("Should catch FoursquareException!");
        }
    }

    @Test
    public void testMapper_OK() throws FoursquareException {
        // Just a cyclic way of mocking this so we have an empty JsonArray that is returned
        when(jsonObject.get("response")).thenReturn(jsonElement);
        when(jsonElement.getAsJsonObject()).thenReturn(jsonObject);
        when(jsonObject.get("groups")).thenReturn(jsonElement);
        when(jsonElement.getAsJsonArray()).thenReturn(jsonArray);
        when(jsonArray.get(0)).thenReturn(jsonElement);
        when(jsonObject.get("items")).thenReturn(jsonElement);

        List<Venue> venues = mapper.map(jsonObject);

        assertTrue(venues.isEmpty());
    }
}
