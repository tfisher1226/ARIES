package aries.codegen.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FilterSet;
import org.apache.tools.ant.util.VectorSet;


public class MyFilterSet extends FilterSet {

    /** Contains a list of parsed tokens */
    private Vector passedTokens;

    /** if a duplicate token is found, this is set to true */
    private boolean duplicateToken = false;

    private boolean recurse = true;

    private int recurseDepth = 0;

    
    public MyFilterSet() {
    	//do nothing
    }

    public MyFilterSet(String token, String value) {
    	addFilter(token, value);
    }

    public MyFilterSet(FilterSet filterSet) {
    	addConfiguredFilterSet(filterSet);
    }

	/**
     * Does replacement on the given string with token matching.
     * This uses the defined begintoken and endtoken values which default
     * to @ for both.
     * This resets the passedTokens and calls iReplaceTokens to
     * do the actual replacements.
     *
     * @param line  The line in which to process embedded tokens.
     * @return      The input string after token replacement.
     */
    public synchronized String replaceTokens(String line) {
        return iReplaceTokens(line);
    }
    
    /**
     * Does replacement on the given string with token matching.
     * This uses the defined begintoken and endtoken values which default
     * to @ for both.
     *
     * @param line  The line to process the tokens in.
     * @return      The string with the tokens replaced.
     */
    private synchronized String iReplaceTokens(String line) {
        Hashtable tokens = getFilterHash();
        Enumeration keys = tokens.keys();
        StringBuffer buf = null;
        
        while (keys.hasMoreElements()) {
            int index = 0; //line.indexOf(beginToken);
        	String token = (String) keys.nextElement();
            buf = new StringBuffer();

            try {
                String value = null;

                int i = 0;
                while (index > -1) {
                    //can't have zero-length token
                    int endIndex = line.indexOf(token, index);
                	//System.out.println(">>> "+endIndex+", "+token+", "+line);
                    if (endIndex == -1) {
                        break;
                    }
                    
                    buf.append(line.substring(i, endIndex));
                    value = (String) tokens.get(token);
                    if (recurse && !value.equals(token)) {
                        // we have another token, let's parse it.
                        value = replaceTokens(value, token);
                    }

                    log("Replacing: " + token + " -> " + value, Project.MSG_VERBOSE);
                    buf.append(value);
                    i = endIndex + token.length();
                    index = line.indexOf(token, i);
                }

                buf.append(line.substring(i));
                line = new String(buf.toString());
                
            } catch (StringIndexOutOfBoundsException e) {
                return line;
            }
        }

        return buf.toString();
    }

    
    /**
     * This parses tokens which point to tokens.
     * It also maintains a list of currently used tokens, so we cannot
     * get into an infinite loop.
     * @param line the value / token to parse.
     * @param parent the parent token (= the token it was parsed from).
     */
    private synchronized String replaceTokens(String line, String parent)
        throws BuildException {
        String beginToken = ""; //getBeginToken();
        String endToken = ""; //getEndToken();
        if (recurseDepth == 0) {
            passedTokens = new VectorSet();
        }
        recurseDepth++;
        if (passedTokens.contains(parent) && !duplicateToken) {
            duplicateToken = true;
            System.out.println(
                "Infinite loop in tokens. Currently known tokens : "
                + passedTokens.toString() + "\nProblem token : " + beginToken
                + parent + endToken + " called from " + beginToken
                + passedTokens.lastElement().toString() + endToken);
            recurseDepth--;
            return parent;
        }
        passedTokens.addElement(parent);
        String value = iReplaceTokens(line);
        if (value.indexOf(beginToken) == -1 && !duplicateToken
                && recurseDepth == 1) {
            passedTokens = null;
        } else if (duplicateToken) {
            // should always be the case...
            if (passedTokens.size() > 0) {
                value = (String) passedTokens.remove(passedTokens.size() - 1);
                if (passedTokens.size() == 0) {
                    value = beginToken + value + endToken;
                    duplicateToken = false;
                }
            }
        } else if (passedTokens.size() > 0) {
            // remove last seen token when crawling out of recursion 
            passedTokens.remove(passedTokens.size() - 1);
        }
        recurseDepth--;
        return value;
    }

}
