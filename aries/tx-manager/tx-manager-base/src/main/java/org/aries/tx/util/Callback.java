package org.aries.tx.util;


public abstract class Callback {
	
    private boolean triggered;

    private boolean failed;
    
    
    public synchronized boolean hasTriggered() {
        return triggered;
    }
    
    public synchronized void setTriggered() {
        triggered = true;
        notifyAll();
    }
    
    public synchronized boolean hasFailed() {
        return failed;
    }
    
    public synchronized void setFailed() {
        failed = true;
        notifyAll();
    }

    public void waitUntilTriggered() {
        waitUntilTriggered(0);
    }
    
    /**
     * Wait until the callback has triggered or failed.
     * @param delay the timeout period in milliseconds.
     */
    public synchronized void waitUntilTriggered(long delay) {
        long endTime = (delay <= 0 ? Long.MAX_VALUE : System.currentTimeMillis() + delay);
        long now = System.currentTimeMillis();
        while(endTime > now && !(triggered || failed)) {
            try {
                wait(endTime - now);
            } catch (InterruptedException ie) {
            	//ignore
            }
            now = System.currentTimeMillis();
        }
    }
}
