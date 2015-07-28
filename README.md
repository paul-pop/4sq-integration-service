ANDigital Fourquare Integration
===============================

h1. Summary
This application is built as both a usable REST API (JSON only at this stage) but also has a web interface on top of it.
I would not do this normally and have them separated, but I felt that just building this for a website would ruin the fun
for me so I have also built it for developers who want to be able to integrate with it.

Things I have used for the integration:
    1) Spring Boot - I have used Spring Boot for minimal application configuration in order to have the application running quickly.
    2) Spring - Spring core and context to make use of Spring's DI and IoC.
    3) Jetty client - used to make calls to Foursquare API
    4) GSON for serialization and deserialization
    5) Vaadin - Java framework for building UI components.

h1. Build and Startup
You will need to have Java 8 JDK and Maven 3 in order to build successfully.
Simply build the application by running: mvn clean install

The application can be started:
    1) From IntelliJ - by running the main method inside the net.paulpop.services.foursquare.Application class.
    2) From the command line - by running the following command from the current folder:
        java -jar target/4sq-integration-service-1.0.jar

h1. Tests
For the sake of this exercise and time, I have only provided unit tests which are run at build time.
I have used TestNG as my main testing framework but also made use of Mockito and PowerMock for mocking.

Note: I have only wrote unit tests for classes that I thought are worth it - skipped POJOs/Builders and the main Application class.
