package net.paulpop.services.foursquare.client;

import org.springframework.beans.factory.annotation.Value;

/**
 * Abstract HTTP client that can be used across multiple client implementations (Jetty, Apache etc.)
 *
 * Created by popp on 28/07/15.
 */
abstract class AbstractFoursquareClient {

    @Value("${4sq.endpoint}")
    protected String endpoint;

    @Value("${4sq.client.timeout}")
    protected Integer timeout;

    @Value("${4sq.credentials.clientid}")
    protected String clientId;

    @Value("${4sq.credentials.clientsecret}")
    protected String clientSecret;

    @Value("${4sq.api.version}")
    protected String apiVersion;

    /**
     * Enforce client initialization that is only run once, preferably by using {@link javax.annotation.PostConstruct}
     * @throws Exception
     */
    abstract void init() throws Exception;
}
