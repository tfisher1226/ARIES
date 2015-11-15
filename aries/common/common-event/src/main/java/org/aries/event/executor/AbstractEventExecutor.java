package org.aries.event.executor;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.common.AbstractEvent;
import org.aries.event.EventAdapter;
import org.aries.event.EventHandler;


public abstract class AbstractEventExecutor<T extends AbstractEvent> extends EventAdapter<T> implements EventHandler<T> {

//	protected abstract EventMulticaster getEventMulticaster();

//	protected abstract AbstractProcess getProcess();
	
	public final static long DEFAULT_TIMEOUT = 120000;

	protected Log log = LogFactory.getLog(getClass());
	
	//@Resource
	//private TimerService timerService;

	private Timer timer;

	private long timeout;
	
	private boolean isClosed;


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
		timer = new Timer("ShipmentScheduledTimer");
		timer.schedule(timeoutHandler, timeout);
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
		log.info("closed");
	}
	
}
