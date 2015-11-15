package org.aries.util.concurrent;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


public class LockUtil {

	private ReentrantLock lock;

	private long lastRequestTime;
	private Date startDatetime;
	private Date lastDatetime;
	private Integer timeout;
	private Integer concurrentRequestTimeout;
	private List<String> conversationIdStack;

	//not synchronized!
	public boolean lockNoWait() {
		return lock.tryLock();
	}

	//not synchronized!
	public boolean lock() {
		try {
			return lock.tryLock(getConcurrentRequestTimeout(), TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public Integer getConcurrentRequestTimeout() {
		//SEAM return concurrentRequestTimeout == null ? Manager.instance().getConcurrentRequestTimeout() : concurrentRequestTimeout;
		return concurrentRequestTimeout;
	}

	//not synchronized!
	public void unlock() {
		lock.unlock();
	}

	public boolean isLockedByCurrentThread() {
		return lock.isHeldByCurrentThread();
	}

}
