package org.aries.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class FutureResult<V> implements Future<V> {

	private volatile V result;

	private volatile Exception problem;

	private final FutureTask<V> resultSyncer;

	
	public FutureResult() {
		Callable<V> resultReturner = new Callable<V>() {
			public V call() throws Exception {
				if (problem != null)
					throw problem;
				return result;
			}
		};
		resultSyncer = new FutureTask<V>(resultReturner);
	}

	public void set(V result) {
		this.result = result;
		resultSyncer.run();
	}

	public void setException(Exception problem) {
		this.problem = problem;
		resultSyncer.run();
	}

	public V get() throws InterruptedException, ExecutionException {
		return resultSyncer.get();
	}

	public V get(long timeout) throws InterruptedException, ExecutionException, TimeoutException {
		return resultSyncer.get(timeout, TimeUnit.MILLISECONDS);
	}

	@Override
	public V get(long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
		return resultSyncer.get(timeout, timeUnit);
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return resultSyncer.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled() {
		return resultSyncer.isCancelled();
	}

	@Override
	public boolean isDone() {
		return resultSyncer.isDone();
	}

}
