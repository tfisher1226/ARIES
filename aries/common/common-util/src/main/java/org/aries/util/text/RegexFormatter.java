package org.aries.util.text;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.text.DefaultFormatter;


/**
 * A regular expression based implementation of <code>AbstractFormatter</code>.
 */
@SuppressWarnings("serial")
public class RegexFormatter extends DefaultFormatter {
    
    private Pattern _pattern;
    
    private Matcher _matcher;

    
    public RegexFormatter() {
        //nothing for now
    }

    /**
     * Creates a regular expression based <code>AbstractFormatter</code>.
     * <code>pattern</code> specifies the regular expression that will
     * be used to determine if a value is legal.
     */
    public RegexFormatter(String pattern) throws PatternSyntaxException {
        this();
        setPattern(Pattern.compile(pattern));
    }

    /**
     * Creates a regular expression based <code>AbstractFormatter</code>.
     * <code>pattern</code> specifies the regular expression that will
     * be used to determine if a value is legal.
     */
    public RegexFormatter(Pattern pattern) {
        this();
        setPattern(pattern);
    }

    /**
     * Sets the pattern that will be used to determine if a value is
     * legal.
     */
    public void setPattern(Pattern pattern) {
        _pattern = pattern;
    }

    /**
     * Returns the <code>Pattern</code> used to determine if a value is
     * legal.
     */
    public Pattern getPattern() {
        return _pattern;
    }

    /**
     * Sets the <code>Matcher</code> used in the most recent test
     * if a value is legal.
     */
    protected void setMatcher(Matcher matcher) {
        _matcher = matcher;
    }

    /**
     * Returns the <code>Matcher</code> from the most test.
     */
    protected Matcher getMatcher() {
        return _matcher;
    }

    /**
     * Parses <code>text</code> returning an arbitrary Object. Some
     * formatters may return null.
     * <p>
     * If a <code>Pattern</code> has been specified and the text
     * completely matches the regular expression this will invoke
     * <code>setMatcher</code>.
     *
     * @throws ParseException if there is an error in the conversion
     * @param text String to convert
     * @return Object representation of text
     */
    public Object stringToValue(String text) throws ParseException {
        Pattern pattern = getPattern();
        if (pattern != null) {
            Matcher matcher = pattern.matcher(text);
            if (!matcher.matches())
                throw new ParseException("Pattern did not match", 0);
            setMatcher(matcher);
            return super.stringToValue(text);
        }
        return text;
    }
}

