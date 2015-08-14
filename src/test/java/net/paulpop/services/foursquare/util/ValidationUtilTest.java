package net.paulpop.services.foursquare.util;

import com.google.gson.JsonElement;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

/**
 * Created by popp on 28/07/15.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonElement.class})
public class ValidationUtilTest extends PowerMockTestCase {

    @Mock private JsonElement jsonElement;

    @BeforeClass
    public void beforeClass() {
        initMocks(this);
    }

    @Test(dataProvider = "mandatoryValues")
    public void testMandatory_OK(Object o) {
        assertEquals(ValidationUtil.mandatory(o), o);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "This parameter is required!")
    public void testMandatory_Null() {
        ValidationUtil.mandatory(null);
    }

    @Test
    public void testNvl_Default() {
        assertEquals(ValidationUtil.nvl(null, "string"), "string");
    }

    @Test
    public void testNvl_OK() {
        assertEquals(ValidationUtil.nvl("string1", "string2"), "string1");
    }

    @DataProvider
    private Object[][] mandatoryValues() {
        return new Object[][] {
                {"string"},
                {""},
                {BigDecimal.ONE},
                {Integer.MAX_VALUE},
                {false}
        };
    }
}
