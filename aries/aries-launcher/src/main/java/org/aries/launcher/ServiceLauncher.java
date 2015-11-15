package org.aries.launcher;


public interface ServiceLauncher {

	public String getServiceId();

	public String getAddress();
	
	public void launch();
	
	public void shutdown();
	
}
