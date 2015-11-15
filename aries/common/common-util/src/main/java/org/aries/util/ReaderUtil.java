package org.aries.util;

import java.io.IOException;
import java.io.Reader;


public class ReaderUtil {
	
	private ReaderUtil() {
    	// This class is not meant to be instantiated. It is a groups static helper methods.
    }
	/** 
     * Dumps the contents of the {@link Reader} to a 
     * String, closing the {@link Reader} when done. 
     */ 
    public static String readToString(Reader reader) throws IOException { 
        StringBuffer buf = new StringBuffer(); 
        try { 
            for(int c = reader.read(); c != -1; c = reader.read()) { 
                buf.append((char)c); 
            } 
            return buf.toString(); 
        } catch (IOException e) { 
            throw e; 
        } finally { 
            try { 
                reader.close(); 
            } catch(Exception e) { 
                // ignored 
            } 
        } 
    }
    
}
