package net.paulpop.services.foursquare.client;

import org.springframework.beans.factory.annotation.Value;

/**
 * Abstract HTTP client that can be used across multiple client implementations (Jetty, Apache etc.)
 * for both BIO and NIO implementations.
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
     * Enforce client startup, preferably by using {@link javax.annotation.PostConstruct}
     *
     * @throws Exception
     */
    abstract void start() throws Exception;

    /**
     * Enforce client termination, preferably as part of a shutdown hook.
     *
     * @throws Exception
     */
    abstract void terminate() throws Exception;
}
