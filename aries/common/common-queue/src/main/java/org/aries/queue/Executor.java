package org.aries.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Provides component for processing a stream of {@link Runnable}s using a thread pool.
 * At maximum capacity, one runnable may be executed simultaneously by each thread in the pool. 
 * 
 * @author tfisher
 *
 */
public class Executor {

	private static final long DEFAULT_TIMEOUT_VALUE = 1;
	
	private static final TimeUnit DEFAULT_TIMEOUT_UNIT = TimeUnit.MINUTES;
	
	private static final String DEFAULT_GROUP = "Executor Group";

	private static final String DEFAULT_NAME = "Executor";

	private static final int DEFAULT_SIZE = 1;

	
	protected ExecutorService executor;

	protected long timeoutValue = DEFAULT_TIMEOUT_VALUE;
	
	protected TimeUnit timeoutUnit = DEFAULT_TIMEOUT_UNIT;

	protected String group = DEFAULT_GROUP;
	
	protected String name = DEFAULT_NAME;
	
	protected int size = DEFAULT_SIZE;

	
	public Executor() {
		//nothing for now
	}
	
	public Executor(String group, String name, int size) {
		this.group = group;
		this.name = name;
		this.size = size;
	}
	
	public long getTimeoutValue() {
		return timeoutValue;
	}

	public void setTimeoutValue(long timeoutValue) {
		this.timeoutValue = timeoutValue;
	}

	public TimeUnit getTimeoutUnit() {
		return timeoutUnit;
	}

	public void setTimeoutUnit(TimeUnit timeoutUnit) {
		this.timeoutUnit = timeoutUnit;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	
	public void initialize() {
		executor = createExecutor();
	}

	
	protected ExecutorService createExecutor() {
		return createExecutor(group, name, size);
	}

	
	//TODO use keep alive var.
	protected ExecutorService createExecutor(String group, String name, int poolSize) {
		SynchronousQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
		ThreadPoolExecutor executor = createThreadPoolExecutor(workQueue, group, name, poolSize);
		executor.prestartAllCoreThreads();
		return executor;
	}

	
	protected ThreadPoolExecutor createThreadPoolExecutor(BlockingQueue<Runnable> workQueue, final String group, final String name, int poolSize) {
		final ThreadGroup threadGroup = new ThreadGroup(group);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, poolSize, 1, TimeUnit.MINUTES, workQueue, new ThreadFactory() {
			private int counter = 0;
			public Thread newThread(Runnable runnable) {
				Thread thread = new Thread(threadGroup, runnable);
				thread.setName("org.aries.queue."+name+"."+counter);
				//TODO check do we need this?
				//thread.setDaemon(true);
				counter++;
				return thread;
			}
		});
		return executor;
	}

	
	public void execute(Runnable command) {
		executor.execute(command);
	}

	public void waitFor() {
		try {
			executor.awaitTermination(timeoutValue, timeoutUnit);
		} catch (InterruptedException e) {
			//ignore
		}
	}
	
	public void shutdown() {
		executor.shutdown();
	}


}
