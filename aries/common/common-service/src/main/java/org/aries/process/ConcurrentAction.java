package org.aries.process;

import java.util.concurrent.atomic.AtomicBoolean;


public class ConcurrentAction {

	private AtomicBoolean runnable = new AtomicBoolean(false);

	private AtomicBoolean started = new AtomicBoolean(false);

	private AtomicBoolean completed = new AtomicBoolean(false);

	private AtomicBoolean aborted = new AtomicBoolean(false);

	protected boolean terminateNormally;
	
	protected boolean terminateFlowWithException;

	protected Object returnValue;
	

	public String getName() {
		return getClass().getSimpleName();
	}
	
	public boolean isRunnable() {
		return runnable.get();
	}
	
	public void setRunnable(boolean value) {
		runnable.getAndSet(value);
	}
	
	public boolean isStarted() {
		return started.get();
	}
	
	public void setStarted(boolean value) {
		started.getAndSet(value);
	}
	
	public boolean isCompleted() {
		return completed.get();
	}
	
	public void setCompleted(boolean value) {
		completed.getAndSet(value);
	}
	
	public boolean isAborted() {
		return aborted.get();
	}

	public void isAborted(boolean value) {
		aborted.getAndSet(value);
	}
	
	public void execute() throws Exception {
		//return null;
	}

	public boolean hasTerminatedNormally() {
		return terminateNormally;
	}

	@SuppressWarnings("unchecked")
	public <T> T getReturnValue() {
		return (T) returnValue;
	}

}
