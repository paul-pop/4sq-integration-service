package net.paulpop.services.foursquare.service;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import net.paulpop.services.foursquare.client.JettyFoursquareClient;
import net.paulpop.services.foursquare.domain.Venue;
import net.paulpop.services.foursquare.domain.VenuesResponse;
import net.paulpop.services.foursquare.exception.FoursquareException;
import net.paulpop.services.foursquare.exception.FoursquareExceptionFactory;
import net.paulpop.services.foursquare.serialization.JsonMapper;
import net.paulpop.services.foursquare.serialization.JsonSerDeser;
import net.paulpop.services.foursquare.util.FoursquareOperation;
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
@PrepareForTest({JettyFoursquareClient.class, JsonObject.class})
public class FoursquareIntegrationServiceImplTest extends PowerMockTestCase {

    @Mock private JsonElement mainJsonElement;
    @Mock private JsonObject mainJsonObject;
    @Mock private JsonElement metaJsonElement;
    @Mock private JsonObject metaJsonObject;

    @Mock private JsonElement statusCodeElement;
    @Mock private JsonElement errorTypeElement;
    @Mock private JsonElement errorDetailElement;

    @Mock private JettyFoursquareClient client;
    @Mock private JsonSerDeser<VenuesResponse> jsonSerDeser;
    @Mock private JsonMapper<JsonObject, List<Venue>> jsonMapper;

    @InjectMocks
    private FoursquareIntegrationServiceImpl service;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
    }

    @Test
    public void testService_OK() throws FoursquareException {
        final int responseCode = 200;
        final String json = "{\"meta\":{\"code\":"+responseCode+"},\"response\":{}}";

        when(statusCodeElement.getAsInt()).thenReturn(responseCode);
        when(metaJsonObject.get("code")).thenReturn(statusCodeElement);
        when(mainJsonElement.getAsJsonObject()).thenReturn(mainJsonObject);
        when(metaJsonElement.getAsJsonObject()).thenReturn(metaJsonObject);
        when(mainJsonObject.get("meta")).thenReturn(metaJsonElement);

        when(client.call(FoursquareOperation.EXPLORE, "London", 1, 1)).thenReturn(json);
        when(jsonSerDeser.deserialize(json)).thenReturn(mainJsonElement);
        when(jsonMapper.map(mainJsonObject)).thenReturn(Lists.newArrayList());

        VenuesResponse result = service.explore("London", 1, 1);

        assertEquals(result.getResponseCode(), 200);
        assertNull(result.getErrorCode());
        assertNull(result.getErrorDetail());
        assertTrue(result.getVenues().isEmpty());
    }

    @Test
    public void testService_InvalidRequest() throws FoursquareException {
        final int responseCode = 400;
        final String errorType = "PARAM_ERROR";
        final String errorDetail = "detail";
        final FoursquareException ex = FoursquareExceptionFactory.getInstance().create(responseCode, errorType, errorDetail);
        final String json = "{\"meta\":{\"code\":"+responseCode+",\"errorType\":\""+errorType+"\",\"errorDetail\":\""+errorDetail+"\"}}";

        when(statusCodeElement.getAsInt()).thenReturn(responseCode);
        when(errorTypeElement.getAsString()).thenReturn(errorType);
        when(errorDetailElement.getAsString()).thenReturn(errorDetail);
        when(mainJsonObject.get("meta")).thenReturn(metaJsonElement);
        when(metaJsonObject.get("code")).thenReturn(statusCodeElement);
        when(metaJsonObject.get("errorType")).thenReturn(errorTypeElement);
        when(metaJsonObject.get("errorDetail")).thenReturn(errorDetailElement);

        when(metaJsonElement.getAsJsonObject()).thenReturn(metaJsonObject);
        when(mainJsonElement.getAsJsonObject()).thenReturn(mainJsonObject);

        when(client.call(FoursquareOperation.EXPLORE, "", 10, 10)).thenReturn(json);
        when(jsonSerDeser.deserialize(json)).thenReturn(mainJsonElement);

        try {
            service.explore("", 10, 10);
        } catch (FoursquareException e) {
            assertEquals(e, ex);
        } catch (Exception e) {
            fail("Should catch FoursquareException!");
        }
    }

    @Test
    public void testService_ThrowsExceptionOnCall() throws FoursquareException {
        FoursquareException ex = FoursquareExceptionFactory.getInstance().create(400, "BAD_REQUEST", "details");
        when(client.call(FoursquareOperation.EXPLORE, null, null, null)).thenThrow(ex);

        try {
            service.explore(null, null, null);
        } catch (FoursquareException e) {
            assertEquals(e, ex);
        } catch (Exception e) {
            fail("Should catch FoursquareException!");
        }
    }

    @Test
    public void testService_ThrowsExceptionOnDeserialize() throws FoursquareException {
        final String json = "{}";
        when(client.call(FoursquareOperation.EXPLORE, null, null, null)).thenReturn(json);
        when(jsonSerDeser.deserialize(json)).thenReturn(JsonNull.INSTANCE);

        try {
            service.explore(null, null, null);
        } catch (IllegalStateException e) {
            assert true; // it is an IllegalStateException because it's not a valid JsonObject
        } catch (Exception e) {
            fail("Should catch IllegalStateException!");
        }
    }

}
