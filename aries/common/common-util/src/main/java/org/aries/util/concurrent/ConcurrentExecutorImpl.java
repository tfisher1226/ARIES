package org.aries.util.concurrent;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.aries.Assert;


public class ConcurrentExecutorImpl implements ConcurrentExecutor {

	private static final int DEFAULT_POOL_SIZE = 3;

	private static final String DEFAULT_THREAD_GROUP = "common.util";
	
	private static int counter = 0;

	private Executor executor;

	private String executorName;
	
	private int poolSize = DEFAULT_POOL_SIZE;


	public ConcurrentExecutorImpl(String executorName) {
		this(executorName, DEFAULT_POOL_SIZE);
	}

	public ConcurrentExecutorImpl(String executorName, int poolSize) {
		Assert.notNull(executorName, "Executor name must be specified");
		Assert.isTrue(poolSize > 0, "Pool size must be specified");
		this.executorName = executorName;
		this.poolSize = poolSize;
		setExecutor(null);
	}

//	public ConcurrentExecutorImpl(Executor executor) {
//		setExecutor(executor);
//	}

	public void setExecutor(Executor executor) {
		this.executor = (executor != null ? executor : createExecutor());
	}

	public Executor getConcurrentExecutor() {
		return this.executor;
	}


	public void execute(Runnable runnable) {
		this.executor.execute(runnable);
	}

	public void execute(Runnable task, long startTimeout) {
		execute(task);
	}

	//does this make sense? what to return?
	public Future<?> submit(Runnable runnable) {
		FutureTask<Object> future = new FutureTask<Object>(runnable, null);
		execute(future);
		return future;
	}

	public <T> Future<T> submit(Callable<T> callable) {
		FutureTask<T> future = new FutureTask<T>(callable);
		execute(future);
		return future;
	}

	
	protected ExecutorService createExecutor() {
		return createExecutor(DEFAULT_THREAD_GROUP, executorName, poolSize);
	}
	
	//TODO use keep alive var.
	protected ExecutorService createExecutor(String threadGroup, String name, int poolSize) {
		SynchronousQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
		ThreadPoolExecutor executor = createThreadPoolExecutor(workQueue, threadGroup, name, poolSize);
		executor.prestartAllCoreThreads();
		return executor;
	}

	protected ThreadPoolExecutor createThreadPoolExecutor(BlockingQueue<Runnable> workQueue, final String threadGroupName, final String threadName, int poolSize) {
		final ThreadGroup threadGroup = new ThreadGroup(threadGroupName+"."+counter);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(poolSize, poolSize, 1, TimeUnit.MINUTES, workQueue, new ThreadFactory() {
			public Thread newThread(Runnable runnable) {
				System.out.println("**** creating Thread: "+threadName+"."+counter);
				Thread thread = new Thread(threadGroup, runnable);
				thread.setName(threadName+"."+counter);
				//TODO check do we need this?
				//thread.setDaemon(true);
				counter++;
				return thread;
			}
		});
		return executor;
	}
	
}
