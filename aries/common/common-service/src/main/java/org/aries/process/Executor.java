package org.aries.process;


public interface Executor<T, E extends Exception> {

	public void register(ConcurrentAction action);
	
	public void execute() throws E;
	
	public T getReturnValue();
	
}
