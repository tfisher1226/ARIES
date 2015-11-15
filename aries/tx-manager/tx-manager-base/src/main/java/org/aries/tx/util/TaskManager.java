package org.aries.tx.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class manages the client side of the task manager
 */
public class TaskManager {

	private static Log log = LogFactory.getLog(TaskManager.class);

	private static final int DEFAULT_MAXIMUM_THREAD_COUNT = 10;

	private static final int DEFAULT_MINIMUM_THREAD_COUNT = 0;

	private static final TaskManager MANAGER = new TaskManager();

	public static TaskManager getManager() {
		return MANAGER;
	}


	private int minimumWorkerCount = DEFAULT_MINIMUM_THREAD_COUNT;

	private int maximumWorkerCount = DEFAULT_MAXIMUM_THREAD_COUNT;

	/** The set of already allocated workers. */
	private Set workerPool = new HashSet();

	/** The current list of tasks. */
	private LinkedList taskList = new LinkedList();

	/** The counter used for naming the threads. */
	private int taskCount;

	/** The number of worker waiting. */
	private int waitingCount;

	/** A flag indicating that shutdown is in progress. */
	private boolean shutdown;


	/**
	 * Private to prevent initialisation.
	 */
	private TaskManager() {
	}

	/**
	 * Queue the task for execution.
	 * 
	 * @param task The task to be executed.
	 * @return true if the task was queued, false otherwise.
	 * 
	 */
	public boolean queueTask(final Task task) {
		synchronized (workerPool) {
			if (shutdown) {
				log.debug("Shutdown in progress, ignoring task");
				return false;
			}
		}

		boolean notify;
		synchronized (taskList) {
			taskList.addLast(task);
			notify = (waitingCount > 0);
			if (notify) {
				log.debug("queueTask: notifying waiting workers: "+waitingCount);
				taskList.notify();
			}
		}

		boolean create;
		synchronized (workerPool) {
			create = ((workerPool.size() < minimumWorkerCount) ||
					((workerPool.size() < maximumWorkerCount) && !notify));
		}

		if (create) {
			log.debug("queueTask: creating worker");
			createWorker();
		} else {
			log.debug("queueTask: queueing task for execution");
		}

		return true;
	}

	/**
	 * Get the minimum worker count for the pool.
	 * 
	 * @return The minimum worker count.
	 */
	public int getMinimumWorkerCount() {
		synchronized (workerPool) {
			return minimumWorkerCount;
		}
	}

	/**
	 * Set the minimum worker count for the pool.
	 * 
	 * @param minimumWorkerCount The minimum worker count.
	 *
	 */
	public void setMinimumWorkerCount(int minimumWorkerCount) {
		synchronized (workerPool) {
			if (shutdown) {
				log.debug("shutdown in progress, ignoring set minimum worker count");
				return;
			}
			this.minimumWorkerCount = minimumWorkerCount < 0 ? DEFAULT_MINIMUM_THREAD_COUNT : minimumWorkerCount;
			if (this.minimumWorkerCount > maximumWorkerCount)
				maximumWorkerCount = this.minimumWorkerCount;
		}

		while (true) {
			boolean create;
			synchronized (workerPool) {
				create = (workerPool.size() < this.minimumWorkerCount);
			}

			if (create) {
				createWorker();
			} else {
				break;
			}
		}
	}

	/**
	 * Get the maximum worker count for the pool.
	 * 
	 * @return The maximum worker count.
	 */
	public int getMaximumWorkerCount() {
		synchronized (workerPool) {
			return maximumWorkerCount;
		}
	}
	
	/**
	 * Set the maximum worker count for the pool.
	 * 
	 * @param maximumWorkerCount The maximum worker count.
	 */
	public void setMaximumWorkerCount(int maximumWorkerCount) {
		synchronized (workerPool) {
			if (shutdown) {
				log.debug("shutdown in progress, ignoring set maximum worker count");
				return;
			}
			this.maximumWorkerCount = maximumWorkerCount < 0 ? DEFAULT_MAXIMUM_THREAD_COUNT : maximumWorkerCount;
			if (minimumWorkerCount > this.maximumWorkerCount)
				minimumWorkerCount = this.maximumWorkerCount;

			synchronized (taskList) {
				if ((workerPool.size() > this.maximumWorkerCount) && (waitingCount > 0)) {
					log.debug("setMaximumWorkerCount: reducing pool size from {"+workerPool.size()+"} to {"+maximumWorkerCount+"}");
					taskList.notify();
				}
			}
		}
	}

	/**
	 * Get the current worker count for the pool.
	 * 
	 * @return The current worker count.
	 */
	public int getWorkerCount() {
		synchronized (workerPool) {
			return workerPool.size();
		}
	}

	/**
	 * Close all threads and reset the task list. This method waits until all
	 * threads have finished before returning.
	 */
	public void shutdown() {
		synchronized(workerPool) {
			if (shutdown) {
				log.debug("Shutdown already in progress");
			} else {
				setMaximumWorkerCount(0);
				shutdown = true;
			}
		}

		while (true) {
			Thread waitThread;
			synchronized (workerPool) {
				Iterator workerPoolIter = workerPool.iterator();
				if (workerPoolIter.hasNext()) {
					waitThread = (Thread) workerPoolIter.next();
				} else {
					waitThread = null;
				}
			}

			if (waitThread == null) {
				break;
			} else {
				try {
					waitThread.join();
				} catch (InterruptedException e) {
					// Ignore
				} 
			}
		}

		synchronized (workerPool) {
			if (shutdown) {
				taskList.clear();
				shutdown = false;
			}
		}
	}

	/**
	 * Get another task from the pool.
	 * 
	 * @return The next task from the pool or null if finished.
	 * 
	 */
	Task getTask() {
		while (true) {
			boolean remove;
			synchronized (workerPool) {
				final int excessCount = workerPool.size() - maximumWorkerCount;
				if (excessCount > 0) {
					log.debug("getTask: releasing thread");
					synchronized (taskList) {
						if (excessCount > 1 && waitingCount > 0) {
							log.debug("getTask: notifying waiting thread about excess count {0}"+excessCount);
							taskList.notify();
						}
					}
					remove = true;
				} else {
					remove = false;
				}
			}

			if (remove) {
				Thread currentThread = Thread.currentThread();
				synchronized (workerPool) {
					workerPool.remove(currentThread);
				}
				return null;
			}

			synchronized (taskList) {
				int numTasks = taskList.size();
				if (numTasks > 0) {
					Task task = (Task) taskList.removeFirst();
					if ((numTasks > 1) && (waitingCount > 0)) {
						taskList.notify();
					}
					log.debug("getTask: returning task");
					return task;
				}
				waitingCount++;
				log.debug("getTask: waiting for task");
				try {
					taskList.wait();
				} catch (InterruptedException e) {
					log.debug("getTask: interrupted");
				} finally {
					waitingCount--;
				}
			}
		}
	}

	/**
	 * Create and register a task worker.
	 */
	private void createWorker() {
		TaskWorker taskWorker = new TaskWorker(this);
		String name = null;
		synchronized (workerPool) {
			name = "TaskWorker-" + ++taskCount;
		}
		Thread thread = new Thread(taskWorker, name);
		thread.setDaemon(true);
		synchronized (workerPool) {
			workerPool.add(thread);
		}
		thread.start();
	}
}
