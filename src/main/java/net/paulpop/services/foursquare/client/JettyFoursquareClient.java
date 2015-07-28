package net.paulpop.services.foursquare.client;

import net.paulpop.services.foursquare.util.FoursquareOperation;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Jetty client implementation for calls to Foursquare API.
 *
 * Created by popp on 28/07/15.
 */
@Component
public final class JettyFoursquareClient extends AbstractFoursquareClient {

    // Required for calls over HTTPS
    private final SslContextFactory sslContextFactory = new SslContextFactory();
    private HttpClient httpClient;

    @Override
    @PostConstruct
    void init() throws Exception {
        // Client configuration - override default values provided by Jetty
        httpClient = new HttpClient(sslContextFactory);
        httpClient.setFollowRedirects(false); // bypass redirects

        // Working with default executor - QueuedThreadPool of 200 threads
        httpClient.start();
    }

    /**
     * Perform a HTTP call on a specified Foursquare operation with the given parameters
     *
     * @param near
     * @param radius
     * @param limit
     * @throws Exception
     */
    public String call(FoursquareOperation operation, String near, Integer radius, Integer limit) throws Exception {

        // Build the GET request for the Foursquare API with the required params
        try {
            // TODO Ideally we should so some caching here so we avoid too many calls to the API
            ContentResponse response = httpClient.newRequest(endpoint.concat(operation.getPath()))
                    .method(HttpMethod.GET)
                    .param("client_id", clientId)
                    .param("client_secret", clientSecret)
                    .param("v", apiVersion)
                    .param("near", near)
                    .param("radius", String.valueOf(radius))
                    .param("limit", String.valueOf(limit))
                    .send();
            return response.getContentAsString();
        } catch (IllegalArgumentException | InterruptedException | TimeoutException | ExecutionException e) {
            throw new Exception("An error occurred while calling the Foursquare API", e);
        }
    }

}
