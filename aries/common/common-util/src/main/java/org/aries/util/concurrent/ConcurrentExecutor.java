package org.aries.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;


public interface ConcurrentExecutor extends Executor {

	/**
	 * Executes the specified @{link Runnable}.
	 * <p>Returns immediately after starting the task.
	 * Nothing is returned here - the outcome of the task must be tracked externally.
	 * @param runnable the @{link Runnable} to execute.
	 * @throws TaskRejectedException if execution is not accepted.
	 */
	public void execute(Runnable runnable);
	
	/**
	 * Executes the specified @{link Runnable}.
	 * @param runnable the @{link Runnable} to execute.
	 * @param startTimeout the time (in milliseconds) the task is expected to start.
	 * @throws TaskTimeoutException if task is not started within timeout.
	 * @throws TaskRejectedException if execution is not accepted.
	 */
	public void execute(Runnable runnable, long startTimeout);

	/**
	 * Submits specified @{link Runnable} for execution, passing back a Future representing 
	 * the execution. The Future returns result of <code>null</code> upon completion.
	 * @param runnable the @{link Runnable} to execute.
	 * @return a Future representing pending completion of the execution.
	 * @throws TaskRejectedException if execution is not accepted.
	 */
	public Future<?> submit(Runnable runnable);

	/**
	 * Submits a @{link Callable} for execution, passing back a Future representing the 
	 * execution. The Future returns the execution's result upon completion.
	 * @param runnable the @{link Runnable} to execute.
	 * @return a Future representing pending completion of the execution.
	 * @throws TaskRejectedException if execution is not accepted.
	 */
	public <T> Future<T> submit(Callable<T> callable);
	
}
