ANDigital Fourquare Integration
===============================

## Summary
This application is built as both a usable REST API (JSON only at this stage) but also has a web interface on top of it.
I would not do this normally and have them separated, but I felt that just building this for a website would ruin the fun
for me so I have also built it for developers who want to be able to integrate with it.

Also, a lot of things are extremely extensible and I have used a lot of interfaces/abstract classes together with generics
just to showcase how I would normally build this - despite the fact that I know it's over-engineered for this particular case :)

Things I have used for the integration:
* **Spring Boot** - I have used Spring Boot for minimal application configuration in order to have the application running quickly.
* **Spring** - Spring core and context to make use of Spring's DI and IoC.
* **Jetty client** - used to make calls to Foursquare API
* **GSON** - for serialization and deserialization
* **Vaadin** - Java framework for building UI components.

## Build and Startup
You will need to have Java 8 JDK and Maven 3 in order to build successfully.
Simply build the application by running: 
```
mvn clean install
```

The application can be started:
* From **IntelliJ** - by running the main method inside the net.paulpop.services.foursquare.Application class.
* From the **command line** - by running the following command from the current folder:
```
java -jar target/4sq-integration-service-1.0.jar
```

## Run
Once the application is started, you can go to:
* **UI**: ```http://localhost:8080/venues/search```
* **REST API** sample call: ```http://localhost:8080/venues/search/rest?place=London&radius=200&limit=10```

## Tests
For the sake of this exercise and time, I have only provided unit tests which are run at build time.
I have used TestNG as my main testing framework but also made use of Mockito and PowerMock for mocking and Spring Test
for Reflection Utils - setting values to @Value annotated fields.

**Note**: I have only wrote unit tests for classes that I thought are worth it, so I skipped POJOs/Builders and
the main Application class. There are no UI tests.
