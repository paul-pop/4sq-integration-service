package net.paulpop.services.foursquare.exception;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by popp on 28/07/15.
 */
public class FoursquareExceptionFactoryTest {

    @Mock private FoursquareException ex1;
    @Mock private FoursquareException ex2;

    @InjectMocks
    private FoursquareExceptionFactory factory;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
    }

    @Test(expectedExceptions = FoursquareException.class)
    public void testCreateException_Simple() throws Exception {
        throw factory.create("Exception", new RuntimeException());
    }

    @Test(expectedExceptions = FoursquareException.class)
    public void testCreateException_Detailed() throws Exception {
        throw factory.create(400, "BAD_REQUEST", "Details");
    }
}
