package net.paulpop.services.foursquare.client;

import net.paulpop.services.foursquare.exception.FoursquareException;
import net.paulpop.services.foursquare.exception.FoursquareExceptionFactory;
import net.paulpop.services.foursquare.util.FoursquareOperation;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static net.paulpop.services.foursquare.util.ValidationUtil.mandatory;

/**
 * Jetty client implementation for calls to Foursquare API.
 *
 * Created by popp on 28/07/15.
 */
@Component
public final class JettyFoursquareClient extends AbstractFoursquareClient {

    private HttpClient httpClient;

    @Override
    @PostConstruct
    void init() throws Exception {
        // Required for calls over HTTPS
        SslContextFactory sslContextFactory = new SslContextFactory();

        // Client configuration - override default values provided by Jetty
        httpClient = new HttpClient(sslContextFactory);
        httpClient.setFollowRedirects(false); // bypass redirects
        httpClient.start(); // working with default executor - QueuedThreadPool of 200 threads
    }

    /**
     * Perform a HTTP call on a specified Foursquare operation with the given parameters
     *
     * @param operation operation to be called
     * @param near
     * @param radius
     * @param limit
     * @throws FoursquareException
     */
    public String call(FoursquareOperation operation, String near, Integer radius, Integer limit) throws FoursquareException {

        // Build the GET request for the Foursquare API with the required params
        try {
            // TODO Ideally we should so some caching here so we avoid too many calls to the API
            ContentResponse response = httpClient.newRequest(endpoint + operation.getPath())
                    .method(HttpMethod.GET)
                    .timeout(timeout, TimeUnit.MILLISECONDS)
                    .param("client_id", clientId)
                    .param("client_secret", clientSecret)
                    .param("v", apiVersion)
                    .param("near", mandatory(near))
                    .param("radius", String.valueOf(radius))
                    .param("limit", String.valueOf(limit))
                    .send();
            return response.getContentAsString();
        } catch (IllegalArgumentException | InterruptedException | TimeoutException | ExecutionException e) {
            throw FoursquareExceptionFactory.getInstance().create("An error occurred while calling the Foursquare API", e);
        }
    }

}
