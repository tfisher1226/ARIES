package org.aries.launcher;


public interface AbstractApplicationLauncherMBean {

    public void start() throws Exception;

    //public void stop() throws Exception;

    public void join() throws Exception;

    public boolean isActive();

	//public Object execute(String[] args);
    
}
