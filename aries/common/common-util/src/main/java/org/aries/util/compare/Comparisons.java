package org.aries.util.compare;


import java.util.Date;

public class Comparisons {
    private Comparisons() {
        // This class is not meant to be instantiated.
        // It is a collection of basic comparison methods.
    }

    public static int compareIntegers(Integer left, Integer right) {
    	if (bothNull(left, right)) {
    		return 0;
    	}
    	
        int nullness = compareNullness(left, right);
        if (nullness != 0) {
            return nullness;
        }

        return left.compareTo(right);
    }

    public static int compareBooleans(Boolean left, Boolean right) {
    	if (bothNull(left, right)) {
    		return 0;
    	}
    	
        int nullness = compareNullness(left, right);
        if (nullness != 0) {
            return nullness;
        }

        return right.compareTo(left);
    }

    public static int compareDates(Date left, Date right) {
    	if (bothNull(left, right)) {
    		return 0;
    	}
    	
    	int nullness = compareNullness(left, right);
        if (nullness != 0) {
            return nullness;
        }

        return right.compareTo(left);
    }

    public static int compareStrings(String left, String right) {
    	if (bothNull(left, right)) {
    		return 0;
    	}
    	
    	int nullness = compareNullness(left, right);
        if (nullness != 0) {
            return nullness;
        }

        return left.compareTo(right);
    }

    private static int compareNullness(Object left, Object right) {
        if (left == null && right != null) {
            return 1;
        } else if (left != null && right == null) {
            return -1;
        } else {
            return 0;
        }
    }
    
    private static boolean bothNull(Object left, Object right) {
    	return left == null && right == null;
    }
}
