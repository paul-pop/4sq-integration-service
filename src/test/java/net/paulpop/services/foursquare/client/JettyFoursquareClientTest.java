package net.paulpop.services.foursquare.client;

import net.paulpop.services.foursquare.exception.FoursquareException;
import net.paulpop.services.foursquare.util.FoursquareOperation;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpMethod;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by popp on 29/07/15.
 */
public class JettyFoursquareClientTest {

    private static final String ENDPOINT = "/api";
    private static final String EMPTY = "";
    private static final Integer TIMEOUT = 10000;

    @Mock private HttpClient httpClient;
    @Mock private Request request;
    @Mock private ContentResponse response;

    @InjectMocks
    private JettyFoursquareClient client;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);

        // Set dummy values to the mocked client properties
        ReflectionTestUtils.setField(client, "endpoint", ENDPOINT);
        ReflectionTestUtils.setField(client, "timeout", TIMEOUT);
        ReflectionTestUtils.setField(client, "clientId", EMPTY);
        ReflectionTestUtils.setField(client, "clientSecret", EMPTY);
        ReflectionTestUtils.setField(client, "apiVersion", EMPTY);

        when(request.method(HttpMethod.GET)).thenReturn(request);
        when(request.timeout(TIMEOUT, TimeUnit.MILLISECONDS)).thenReturn(request);
        when(request.param("client_id", EMPTY)).thenReturn(request);
        when(request.param("client_secret", EMPTY)).thenReturn(request);
        when(request.param("v", EMPTY)).thenReturn(request);
    }

    @Test(priority = 1)
    public void testInitAndDestroy() throws Exception {
        client.start();
        client.terminate();

        assert true;
    }

    @Test
    public void testClient_OK() throws Exception {
        //given
        final String json = "{}";
        when(request.param("near", "abc")).thenReturn(request);
        when(request.param("radius", "10")).thenReturn(request);
        when(request.param("limit", "10")).thenReturn(request);
        when(request.send()).thenReturn(response);
        when(response.getContentAsString()).thenReturn(json);
        when(httpClient.newRequest(ENDPOINT + FoursquareOperation.EXPLORE.getPath())).thenReturn(request);

        //when
        String result = client.call(FoursquareOperation.EXPLORE, "abc", 10, 10);

        //then
        assertNotNull(result);
        assertEquals(result, json);
    }

    @Test(expectedExceptions = FoursquareException.class)
    public void testClient_InvalidParam() throws Exception {
        //given
        when(request.param("near", null)).thenReturn(request);
        when(request.param("radius", null)).thenReturn(request);
        when(request.param("limit", null)).thenReturn(request);
        when(request.send()).thenReturn(response);
        when(httpClient.newRequest(ENDPOINT + FoursquareOperation.EXPLORE.getPath())).thenReturn(request);

        //when
        client.call(FoursquareOperation.EXPLORE, null, null, null);
    }

    @Test(expectedExceptions = FoursquareException.class)
    public void testClient_FailedCall() throws Exception {
        //given
        when(request.param("near", "abc")).thenReturn(request);
        when(request.param("radius", "10")).thenReturn(request);
        when(request.param("limit", "10")).thenReturn(request);
        when(request.send()).thenThrow(new InterruptedException());
        when(httpClient.newRequest(ENDPOINT + FoursquareOperation.EXPLORE.getPath())).thenReturn(request);

        //when
        client.call(FoursquareOperation.EXPLORE, "abc", 10, 10);
    }
}
