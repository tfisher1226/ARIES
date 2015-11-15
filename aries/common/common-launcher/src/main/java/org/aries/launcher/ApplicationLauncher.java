package org.aries.launcher;

import org.aries.event.tracker.EventTracker;


public interface ApplicationLauncher {

	public void setProgram(Program program);

	public void initialize() throws Exception;
	
	public void start() throws Exception;

	public void join() throws Exception;

	public void stop() throws Exception;

	public void setEventTracker(EventTracker eventTracker);

	public void addWaitToken(String token);

	public void waitFor() throws Exception;

}
