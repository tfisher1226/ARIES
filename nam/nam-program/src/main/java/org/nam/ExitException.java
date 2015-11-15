package org.nam;


@SuppressWarnings("serial")
public class ExitException extends Exception {

    private int exitCode;

    
    public ExitException(int exitCode) {
        this.exitCode = exitCode;
    }
    
    public int getExitCode() {
    	return exitCode;
    }
    
}
