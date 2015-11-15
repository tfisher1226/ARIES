package org.aries.util;


public class EqualsUtil {

    private EqualsUtil() {
    	// This class is not meant to be instantiated. It is a groups static helper methods.
    }
    
	/**
	 * Determines if given objects are equal. Basically handles nulls without throwing exception.
	 * Returns true if both are null or false if either one is null.
	 * @param o1 first Object to compare
	 * @param o2 second Object to compare
	 * @return whether the given objects are equal
	 */
	public static boolean equals(Object object1, Object object2) {
		/*
		if (object1 == null && object2 == null)
			return true;
		if (object1 == null && object2 != null)
			return false;
		if (object1 != null && object2 == null)
			return false;
		return object1.equals(object2);
		*/
		
		return object1 == null ? object2 == null : object1.equals(object2);
	}

    public static boolean equals(Class class1, Class class2) {
    	if (class1 == null && class2 == null)
			return true;
    	if (class1 == null || class2 == null)
    		return false;
    	//TODO check classloader is same
    	String name1 = class1.getName();
    	String name2 = class2.getName();
		return name1.equals(name2);
    }

    public static boolean equals(boolean aThis, boolean aThat){
    	return aThis == aThat;
    }

    static public boolean equals(char aThis, char aThat){
    	return aThis == aThat;
    }

    static public boolean equals(long aThis, long aThat){
        /*
        * Implementation Note
        * Note that byte, short, and int are handled by this method, through
        * implicit conversion.
        */
        return aThis == aThat;
    }

    static public boolean equals(float aThis, float aThat){
    	return Float.floatToIntBits(aThis) == Float.floatToIntBits(aThat);
    }

    static public boolean equals(double aThis, double aThat){
    	return Double.doubleToLongBits(aThis) == Double.doubleToLongBits(aThat);
    }

    /**
      * Possibly-null object field.
      *
      * Includes type-safe enumerations and collections, but does not include
      * arrays. See class comment.
      */
//    static public boolean areEqual(Object aThis, Object aThat){
//        return aThis == null ? aThat == null : aThis.equals(aThat);
//    }
}
