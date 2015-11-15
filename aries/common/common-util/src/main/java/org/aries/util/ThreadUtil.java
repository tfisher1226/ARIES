package org.aries.util;


public class ThreadUtil {
	private ThreadUtil() {
    	// This class is not meant to be instantiated. It is a groups static helper methods.
    }
	
	//TODO obtain threads here from a thread pool i.e. use a PooledExecutor
	//TODO do this when we analyze all threads application wide 
	public static void execute(Runnable runnable) {
		Thread thread = new Thread(runnable);
		thread.start();
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
