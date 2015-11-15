package org.aries.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ExecutorFactory {

    public static ExecutorService createExecutor(int poolSize) {
    	SynchronousQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, poolSize, 1, TimeUnit.MINUTES, workQueue);
		executor.prestartAllCoreThreads();
		return executor;
    }

    //TODO need to assure threadGroupName is unique (or not previously existing...)
	public static ExecutorService createSingleThreadExecutor(String threadGroupName) {
		ThreadGroup threadGroup = new ThreadGroup(threadGroupName);
		ExecutorService executor = createSingleThreadExecutor(threadGroup);
		return executor;
	}
	
	public static ExecutorService createSingleThreadExecutor(final ThreadGroup threadGroup) {
		return Executors.newSingleThreadExecutor(new ThreadFactory() {
			public Thread newThread(Runnable runner) {
				return new Thread(threadGroup, runner);
			}
		});
	}

}
