package org.aries.util.text;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class CurrencyUtilTest extends TestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
    public void testParseCurrency() throws Exception {
		verify("123,456.78", 123456.78d);
	}
	
	protected void verify(String value, double expected) throws Exception {
		Double actual = CurrencyUtil.parseCurrency(value);
		//assertEquals(expected, actual, 0.0);
        if (actual != expected) {
        	fail("Expected=" + expected + ", actual=" + actual);
        }
	}

	@Test
    public void testCurrencyToBigDecimalFormat() throws Exception {           
        verify("123,456.78", "123456.78");
        verify("123.456,78", "123456.78");
        verify("123.45", "123.45");
        verify("1.234", "1234");
        verify("12", "12");
        verify("12.1", "12.1");
        verify("1.13", "1.13");
        verify("1.1", "1.1");
        verify("1,2", "1.2");
        verify("1", "1");                          
    }

	protected void verify(String value, String expected) throws Exception {
        String actual = CurrencyUtil.currencyToBigDecimalFormat(value);
        if (!actual.equals(expected)) {
        	fail("Expected=" + expected + ", actual=" + actual);
        }
    }

}
