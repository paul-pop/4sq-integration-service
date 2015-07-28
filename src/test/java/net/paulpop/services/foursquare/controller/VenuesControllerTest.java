package net.paulpop.services.foursquare.controller;

import com.google.common.collect.Lists;
import net.paulpop.services.foursquare.domain.VenuesResponse;
import net.paulpop.services.foursquare.exception.FoursquareException;
import net.paulpop.services.foursquare.exception.FoursquareExceptionFactory;
import net.paulpop.services.foursquare.serialization.JsonSerDeser;
import net.paulpop.services.foursquare.service.FoursquareIntegrationService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

/**
 * Created by popp on 29/07/15.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({VenuesController.class})
public class VenuesControllerTest extends PowerMockTestCase {

    @Mock private FoursquareIntegrationService service;
    @Mock private JsonSerDeser<VenuesResponse> jsonSerDeser;
    @Mock private VenuesResponse venuesResponse;

    @InjectMocks
    private VenuesController controller;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);

        when(venuesResponse.getResponseCode()).thenReturn(200);
        when(venuesResponse.getVenues()).thenReturn(Lists.newArrayList());
    }

    @Test
    public void testSearchVenues_OK() throws Exception {
        // given
        final String json = "{\"venues\":[],\"responseCode\":200}";
        when(service.explore("X", 1, 1)).thenReturn(venuesResponse);
        when(jsonSerDeser.serialize(venuesResponse)).thenReturn(json);

        // when
        String result = controller.searchVenues("X", 1, 1);

        // then
        verify(jsonSerDeser, times(1)).serialize(venuesResponse);
        assertNotNull(result);
        assertEquals(result, json);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testSearchVenues_SerializationFAIL() throws FoursquareException {
        // given
        when(service.explore("X", 1, 1)).thenReturn(venuesResponse);
        when(jsonSerDeser.serialize(venuesResponse)).thenThrow(new RuntimeException());

        // when
        controller.searchVenues("X", 1, 1);
    }

    @Test
    public void testSearchVenues_ServiceCallFAIL() throws Exception {
        // given
        final int responseCode = 400;
        final String errorCode = "FAILED_GEOCODE";
        final String errorDetail = "error";
        final String json = "{\"responseCode\":"+responseCode+",\"errorCode\":\""+errorCode+"\",\"errorDetail\":\""+errorDetail+"\"}";

        when(venuesResponse.getResponseCode()).thenReturn(responseCode);
        when(venuesResponse.getErrorCode()).thenReturn(errorCode);
        when(venuesResponse.getErrorDetail()).thenReturn(errorDetail);
        PowerMockito.whenNew(VenuesResponse.class).withNoArguments().thenReturn(venuesResponse);

        FoursquareException ex = FoursquareExceptionFactory.getInstance().create(responseCode, errorCode, errorDetail);
        when(service.explore("X", 1, 1)).thenThrow(ex);
        when(jsonSerDeser.serialize(venuesResponse)).thenReturn(json);

        // when
        String result = controller.searchVenues("X", 1, 1);

        // then
        verify(jsonSerDeser, times(1)).serialize(venuesResponse);
        assertNotNull(result);
        assertEquals(result, json);
    }

}
