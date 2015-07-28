package net.paulpop.services.foursquare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Main entry point of the application. The purpose of this class it to start the Spring application context,
 * initialize all beans and perform overall application configuration.
 *
 * Created by popp on 28/07/15.
 */
@SpringBootApplication
@PropertySource("classpath:service-defaults.properties")
public class Application {

    // Main method that starts up the Spring application context using Spring Boot.
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
