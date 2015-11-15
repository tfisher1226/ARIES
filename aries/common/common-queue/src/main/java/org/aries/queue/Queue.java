package org.aries.queue;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


/**
 * Implementation of a queue.  The type of object placed in 
 * the queue is generic but must be specified at queue creation time.
 * 
 * @author tfisher
 */
public class Queue<T> implements Serializable {

	private static final long READ_TIMEOUT = 3000; //ms;

	private static final int WRITE_TIMEOUT = 3000; //ms

	private String queueId;

	private Object mutex;

	private BlockingQueue<T> queue;
	
	
    public Queue(String queueId) {
    	this.queueId = queueId;
    	initialize();
    }
    
	protected void initialize() {
		mutex = new Object();
		queue = new LinkedBlockingQueue<T>();
	}

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
	

	public T get() {
		synchronized (mutex) {
			try {
				T object = queue.poll(READ_TIMEOUT, TimeUnit.SECONDS);
				return object;
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public void put(T object) {
		synchronized (mutex) {
			int iterations = 3;
			int sleepTime = WRITE_TIMEOUT/iterations;
			//loop here to carry-out x-number of retries...
			for (int i=0; i < iterations; i++) {
				boolean status = queue.offer(object);
				if (status)
					break;
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}
	
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		@SuppressWarnings("unchecked")
		Queue<T> other = (Queue<T>) object;
		if (this.getQueueId() == null || other.getQueueId() == null)
			return this == other;
		if (this.getQueueId() != other.getQueueId())
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		if (getQueueId() != null)
			return getQueueId().hashCode();
		return 0;
	}

}
