/*
 * MockProcessLauncher.java
 * Created on Jun 2, 2005
 * 
 * 
 */
package org.aries.launcher;

import org.aries.launcher.ProcessLauncher;


public class MockProcessLauncher extends ProcessLauncher {

    public boolean wasStarted;

    public boolean wasRun;
    
    public void start(String command) {
        wasStarted = true;
    }
    
    public String execute(String[] command) {
        wasRun = true;
        return null;
    }
}
