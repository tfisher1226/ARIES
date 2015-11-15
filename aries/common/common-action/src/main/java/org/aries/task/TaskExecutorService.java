package org.aries.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.aries.util.concurrent.FutureResult;


public class TaskExecutorService extends ThreadPoolExecutor {

	private FutureResult<Boolean> futureResult = new FutureResult<Boolean>();
	
	private Throwable exception;
	
	
	public TaskExecutorService(int nThreads) {
        this(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }
	
	public TaskExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	public Throwable getException() {
		return exception;
	}
	
	protected void afterExecute(Runnable runnable, Throwable exception) { 
		boolean status = exception == null;
		this.exception = exception;
		futureResult.set(status);
	}
	
	public boolean waitForTermination() throws Exception {
		return futureResult.get();
	}

	public boolean waitForTermination(long timeout) throws Exception {
		return futureResult.get();
	}

}

