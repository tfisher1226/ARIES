package org.aries.tx.util;

import java.util.Timer;


public class TransportTimer {
	
    private static Timer TIMER = new Timer(true);
    
    private static long TIMEOUT = 1200000;//20 min.
    //private static long TIMEOUT = 30000;

    private static long PERIOD = 5000;

    private static long MAX_PERIOD = 300000;

//    static {
//        setTransportPeriod(wscEnvironmentBean.getInitialTransportPeriod());
//        setMaximumTransportPeriod(wscEnvironmentBean.getMaximumTransportPeriod());
//        setTransportTimeout(wscEnvironmentBean.getTransportTimeout());
//    }

    
    public static Timer getTimer() {
        return TIMER;
    }
    
    public static void setTransportTimeout(long timeout) {
       TIMEOUT = timeout; 
    }
    
    public static long getTransportTimeout() {
        return TIMEOUT;
    }
    
    public static void setTransportPeriod(long period) {
       PERIOD = period;
    }
    
    public static long getTransportPeriod() {
        return PERIOD;
    }

    public static void setMaximumTransportPeriod(long period) {
       MAX_PERIOD = period;
    }

    public static long getMaximumTransportPeriod() {
        if (MAX_PERIOD < PERIOD)
            return PERIOD;
        return MAX_PERIOD;
    }

}
