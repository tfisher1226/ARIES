package org.aries.process;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TimeoutHandler {

	public final static long DEFAULT_TIMEOUT = 120000;

	protected Log log = LogFactory.getLog(getClass());
	
	//@Resource
	//private TimerService timerService;

	private String name;
	
	private Timer timer;

	private long timeout;
	
	private boolean isClosed;

	private Runnable runnable;


	public TimeoutHandler(String name, Runnable runnable) {
		this.name = name;
		this.runnable = runnable;
	}
	
	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	protected boolean isClosed() {
		return isClosed;
	}
	
	protected void shutdownTimer() {
		timeout = DEFAULT_TIMEOUT;
		if (timer != null)
			timer.cancel();
	}

	protected void createTimer(TimerTask timeoutHandler) {
		createTimer(timeoutHandler, timeout);
	}
	
	protected void createTimer(TimerTask timeoutHandler, long timeout) {
		isClosed = false;
		//long duration = 3000;
		//timer = timerService.createTimer(duration, null);
		timer = new Timer(name);
		timer.schedule(timeoutHandler, timeout);
	}

	
	//@Override
	public void initialize() {
		shutdownTimer();
		timeout = DEFAULT_TIMEOUT;
	}
	
	//@Override
	public void startTimer() {
		//shutdownTimer();
		createTimer(createTimeoutHandler(), timeout);
		log.info(name+": timer started, timeout="+timeout);
	}

	//@Override
	public void reset() {
		close();
		timeout = DEFAULT_TIMEOUT;
	}
	
	//@Override
	protected TimerTask createTimeoutHandler() {
		return new TimerTask() {
			public void run() {
				reset();
				log.info(name+": timed-out");
				runnable.run();
			}
		};
	}
	
	
//	protected void addEventListener(EventListener<T> eventListener) {
//		getEventMulticaster().addEventListener(getEventId(), eventListener);
//	}

//	protected void removeEventListener(EventListener<T> eventListener) {
//		getEventMulticaster().removeEventListener(getEventId(), this);
//	}

	public void close() {
		shutdownTimer();
//		removeEventListener(this);
		log.info(name+": timer closed");
	}

}
