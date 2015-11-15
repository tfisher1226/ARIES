package org.aries;


public interface Executable extends Runnable {

	public boolean isInitialized();

	public boolean isValid();
	
	public boolean isStarted();
    
	public boolean isClosing();
    
	public boolean isClosed();
	
	public void initialize() throws Exception; 
	
	public void validate() throws Exception;
	
	public void start() throws Exception;
	
	public void stop() throws Exception;

	public void join() throws Exception;
	
	public void reset() throws Exception;
	
    public void close() throws Exception;

}
