package org.aries.util.text;

import java.text.ParseException;
import java.util.regex.PatternSyntaxException;

import org.aries.util.text.RegexFormatter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class RegexFormatterTest extends TestCase {
    
    private RegexFormatter fixture;
    
    
	public static void main(String[] args) {
		junit.textui.TestRunner.run(RegexFormatterTest.class);
	}

	public static Test suite() {
		return new TestSuite(RegexFormatterTest.class);
	}


	public RegexFormatterTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        fixture = null;
        super.tearDown();
    }

    
    public void testRegexFormatterString_BadRegex1() {
        String INCOMPLETE_REGEX = "\\p{Alpha}{10,";
        try {
            fixture = new RegexFormatter(INCOMPLETE_REGEX);
            fail("Exception should have been thrown");
        } catch (PatternSyntaxException e) {
            String expectedMessage = "Unclosed counted closure near index 13";
            assertTrue("Exception message should be correct", e.getMessage().startsWith(expectedMessage));
        }
    }
    
    public void testRegexFormatterString_BadRegex2() {
        String INCOMPLETE_REGEX = "\\p{lpha}{10,}";
        try {
            fixture = new RegexFormatter(INCOMPLETE_REGEX);
            fail("Exception should have been thrown");
        } catch (PatternSyntaxException e) {
            String expectedMessage = "Unknown character property name {lpha} near index 7";
            assertTrue("Exception message should be correct", e.getMessage().startsWith(expectedMessage));
        }
    }
    
    public void testRegexFormatterString_EmptyRegex() {
        String EMPTY_REGEX = "";
        try {
            fixture = new RegexFormatter(EMPTY_REGEX);
            assertEquals("Flags should be correct", 0, fixture.getPattern().flags());
            assertEquals("Pattern should be equal", EMPTY_REGEX, fixture.getPattern().pattern());
        } catch (PatternSyntaxException e) {
            fail("Exception should not be thrown");
        }
    }

    public void testRegexFormatterString_GoodRegex() {
        String COMPLETE_REGEX = "\\p{Alpha}{10,}";
        try {
            fixture = new RegexFormatter(COMPLETE_REGEX);
            assertEquals("Flags should be correct", 0, fixture.getPattern().flags());
            assertEquals("Pattern should be equal", COMPLETE_REGEX, fixture.getPattern().pattern());
        } catch (PatternSyntaxException e) {
            fail("Exception should not be thrown");
        }
    }

    public void testStringToValueString_IncompleteAlphaInput() {
        String COMPLETE_REGEX = "\\p{Alpha}{4,}";
        try {
            fixture = new RegexFormatter(COMPLETE_REGEX);
            fixture.stringToValue("abc");
            fail("Exception should have been thrown");
        } catch (PatternSyntaxException e) {
            fail("Exception should not be thrown");
        } catch (ParseException e) {
            String expectedMessage = "Pattern did not match";
            assertEquals("Exception should have been thrown", expectedMessage, e.getMessage());
        }
    }

    public void testStringToValueString_AcceptableAlphaInput() {
        String COMPLETE_REGEX = "\\p{Alpha}{4,}";
        try {
            String input = "abcd";
            fixture = new RegexFormatter(COMPLETE_REGEX);
            Object object = fixture.stringToValue(input);
            assertTrue("Returned object should have correct type", object instanceof String);
            String output = (String) object;
            assertEquals("Returned object should be correct", input, output);
        } catch (PatternSyntaxException e) {
            fail("Exception should not be thrown");
        } catch (ParseException e) {
            fail("Exception should not be thrown");
        }
    }

    public void testStringToValueString_UnacceptableNumericalInput() {
        String COMPLETE_REGEX = "\\d{3}-\\d{4}";
        try {
            String input = "555-ab12";
            fixture = new RegexFormatter(COMPLETE_REGEX);
            fixture.stringToValue(input);
            fail("Exception should have been thrown");
        } catch (PatternSyntaxException e) {
            fail("Exception should not be thrown");
        } catch (ParseException e) {
            String expectedMessage = "Pattern did not match";
            assertEquals("Exception should have been thrown", expectedMessage, e.getMessage());
        }
    }

    public void testStringToValueString_AcceptableNumericalInput() {
        String COMPLETE_REGEX = "\\d{3}-\\d{4}";
        try {
            String input = "555-1212";
            fixture = new RegexFormatter(COMPLETE_REGEX);
            Object object = fixture.stringToValue(input);
            assertTrue("Returned object should have correct type", object instanceof String);
            String output = (String) object;
            assertEquals("Returned object should be correct", input, output);
        } catch (PatternSyntaxException e) {
            fail("Exception should not be thrown");
        } catch (ParseException e) {
            fail("Exception should not be thrown");
        }
    }
}
