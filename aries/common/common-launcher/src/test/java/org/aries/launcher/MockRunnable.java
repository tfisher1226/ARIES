package org.aries.launcher;


public class MockRunnable implements Runnable {

    private boolean wasRun;

    
    public boolean wasRun() {
        return wasRun;
    }
    
    public void run() {
        wasRun = true;
    }
    
}
