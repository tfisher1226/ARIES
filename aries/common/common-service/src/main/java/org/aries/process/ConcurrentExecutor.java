package org.aries.process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ConcurrentExecutor<T, E extends Exception> implements Executor<T, E> {

	private static Log log = LogFactory.getLog(ConcurrentExecutor.class);
	
	private List<ConcurrentAction> actions = new ArrayList<ConcurrentAction>();

	private CountDownLatch latch;
	
	private Object mutex = new Object();
	
	private T returnValue;
	
	
	public T getReturnValue() {
		return returnValue;
	}
	
	public void register(ConcurrentAction action) {
		//log.debug("Register action: "+action.getClass().getSimpleName());
		actions.add(action);
	}
	
	public void execute() throws E {
		log.debug("Starting");
		latch = new CountDownLatch(actions.size());
		startRunnableActions();
		waitForCompletion();
	}

	/**
	 * Causes the current thread to wait until the latch
	 * has counted down to zero, unless the thread is
	 * interrupted.
	 */
	protected final void waitForCompletion() {
		try {
			latch.await();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			throw new Error(e);
		}
	}
	
	protected boolean startRunnableActions() {
		List<ConcurrentAction> runnableActions = getRunnableActions();
		if (runnableActions.size() == 0)
			return false;
		Iterator<ConcurrentAction> iterator = runnableActions.iterator();
		while (iterator.hasNext()) {
			ConcurrentAction action = iterator.next();
			start(action);
		}
		return true;
	}

	protected void start(ConcurrentAction action) {
		Runnable runnable = createRunnable(action);
		Thread thread = new Thread(runnable);
		thread.start();
	}

	protected Runnable createRunnable(final ConcurrentAction action) {
		return new Runnable() {
			public void run() {
				try {
					log.debug(action.getName()+" Started");
					action.setStarted(true);
					action.execute();
					action.setCompleted(true);
					log.debug(action.getName()+" Completed");
						
				} catch (Throwable e) {
					action.isAborted(true);
					log.debug(action.getName()+" Aborted");
				}
				
				if (action.hasTerminatedNormally()) {
					returnValue = action.getReturnValue();
					releaseLatch();
				} else {
					latch.countDown();
					startRunnableActions();
					//releaseLatch();
				}
			}
		};
	}

	protected List<ConcurrentAction> getRunnableActions() {
		synchronized (mutex) {
			List<ConcurrentAction> runnableActions = new ArrayList<ConcurrentAction>();
			Iterator<ConcurrentAction> iterator = actions.iterator();
			while (iterator.hasNext()) {
				ConcurrentAction action = iterator.next();
				if (!action.isCompleted() && !action.isAborted() && !action.isStarted() && action.isRunnable()) {
					log.debug(action.getName()+" Ready");
					runnableActions.add(action);
				}
			}
			return runnableActions;
		}
	}

	protected void releaseLatch() {
		long count = latch.getCount();
		for (int i=0; i < count; i++)
			latch.countDown();
	}
	
}
