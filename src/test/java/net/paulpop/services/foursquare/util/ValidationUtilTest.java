package net.paulpop.services.foursquare.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * Created by popp on 28/07/15.
 */
public class ValidationUtilTest {

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
