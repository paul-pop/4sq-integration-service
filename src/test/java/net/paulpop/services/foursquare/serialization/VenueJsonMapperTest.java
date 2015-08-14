package net.paulpop.services.foursquare.serialization;

import com.google.common.collect.Lists;
import net.paulpop.services.foursquare.domain.Venue;
import net.paulpop.services.foursquare.domain.external.FoursquareGroup;
import net.paulpop.services.foursquare.domain.external.FoursquareItem;
import net.paulpop.services.foursquare.domain.external.FoursquareRoot;
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
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Created by popp on 29/07/15.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FoursquareRoot.class, FoursquareGroup.class, FoursquareItem.class, FoursquareException.class})
public class VenueJsonMapperTest extends PowerMockTestCase {

    @Mock private FoursquareRoot.FoursquareResponse response;
    @Mock private FoursquareGroup group;
    @Mock private FoursquareItem item;

    @InjectMocks
    private VenueJsonMapper mapper;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
    }

    @Test
    public void testMapper_OK() throws FoursquareException {
        when(response.getGroups()).thenReturn(Lists.newArrayList(group));
        when(group.getItems()).thenReturn(Lists.newArrayList());

        List<Venue> venues = mapper.map(response);
        assertTrue(venues.isEmpty());
    }

    @Test
    public void testMapper_ThrowsException() {
        when(response.getGroups()).thenThrow(new RuntimeException());

        try {
            mapper.map(response);
            fail("Should not be here!");
        } catch (FoursquareException e) {
            assertTrue(e.getCause() instanceof RuntimeException);
            assertTrue(e.getMessage().startsWith("Exception occurred when mapping data from Foursquare API"));
        } catch (Exception e) {
            fail("Should catch FoursquareException!");
        }
    }

}
