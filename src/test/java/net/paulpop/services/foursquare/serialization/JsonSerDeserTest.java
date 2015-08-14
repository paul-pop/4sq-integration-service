package net.paulpop.services.foursquare.serialization;

import org.mockito.InjectMocks;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created by popp on 28/07/15.
 */
public class JsonSerDeserTest {

    private StubObject obj;

    @InjectMocks
    private JsonSerDeser<StubObject> jsonSerDeser;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);

        obj = new StubObject(1, "X");
    }

    @Test
    public void testSerialize_O1K() {
        String result = jsonSerDeser.serialize(obj);

        assertEquals(result, StubObject.OBJ);
    }

    @Test
    public void testSerialize_OK() {
        String result = jsonSerDeser.serialize(obj);

        assertEquals(result, StubObject.OBJ);
    }

    @Test
    public void testSerialize_FAIL() {
        String result = jsonSerDeser.serialize(null);

        assertEquals(result, "null");
    }

    @Test
    public void testDeserialize_OK() {
        StubObject result = jsonSerDeser.deserialize(StubObject.OBJ, StubObject.class);

        assertEquals(result.field1, 1);
        assertEquals(result.field2, "X");
    }

    @Test
    public void testDeserialize_FAIL() {
        StubObject result = jsonSerDeser.deserialize(null, StubObject.class);

        assertNull(result);
    }

    /**
     * Just a stub object user for serialization/deserialization testing
     */
    private class StubObject {

        private static final String OBJ = "{\"field1\":1,\"field2\":\"X\"}";

        private int field1;
        private String field2;

        public StubObject(int field1, String field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }
}
