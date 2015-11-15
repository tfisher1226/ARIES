package org.aries;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractExecutable /*extends AbstractBean*/ implements Executable {

	protected final Log log = LogFactory.getLog(getClass());
	
    private AtomicBoolean _initialized;
    
    private AtomicBoolean _starting;

    private AtomicBoolean _started;

    private AtomicBoolean _stopping;

    private AtomicBoolean _stopped;
    
    private AtomicBoolean _closing;

    private AtomicBoolean _closed;
    
    protected Thread _thread;
	
    protected Object _mutex;
    
    
    public AbstractExecutable() {
    	_initialized = new AtomicBoolean(false);
    	_starting = new AtomicBoolean(false);
    	_started = new AtomicBoolean(false);
    	_stopping = new AtomicBoolean(false);
    	_stopped = new AtomicBoolean(false);
    	_closing = new AtomicBoolean(false);
    	_closed = new AtomicBoolean(false);
    	_mutex = new Object();
    }

    public boolean isInitialized() {
		return _initialized.get();
	}

	public void setInitialized(boolean value) {
		_initialized.set(value);
	}

	public boolean isStarting() {
		return _starting.get();
	}
    
	public void setStarting(boolean value) {
		_starting.set(value);
	}

	public boolean isStarted() {
		return _started.get();
	}
    
	public void setStarted(boolean value) {
		_started.set(value);
	}

	public boolean isStopping() {
		return _stopping.get();
	}
    
	public void setStopping(boolean value) {
		_stopping.set(value);
	}

	public boolean isStopped() {
		return _stopped.get();
	}
    
	public void setStopped(boolean value) {
		_stopped.set(value);
	}

	public boolean isClosing() {
		return _closing.get();
	}
    
	public void setClosing(boolean value) {
		_closing.set(value);
	}

	public boolean isClosed() {
		return _closed.get();
	}
    
	public void setClosed(boolean value) {
		_closed.set(value);
	}

    public boolean isOpen() {
		return !isStopping() && !isStopped() && !isClosing() && !isClosed();
	}

	public boolean isValid() {
		return isInitialized() && isStarted() && !isStopping() && !isStopped() && !isClosing() && !isClosed();
	}

	public void validate() throws Exception {
		Assert.isTrue(isValid());
	}

    public void initialize() throws Exception {
    	synchronized (_mutex) {
	    	if (isInitialized())
	    		throw new Exception("Already initialized");
	        //nothing by default
	        setInitialized(true);
	    }
	}
	
    public void start() throws Exception {
    	if (isStarted())
    		throw new Exception("Already started");
    	setStarting(true);
    	synchronized (_mutex) {
    		try {
		        _thread = new Thread(this);
		        _thread.start();
		    	setStarting(false);
		        setStarted(true);
		        setStopped(false);
		        setClosed(false);
		    	if (log.isTraceEnabled())
		    		log.trace("Started");
    		} finally {
    			//nothing for now
    		}
	    }
    }
    
	public void run() {
		try {
			while (isValid() && _thread != null && !_thread.isInterrupted()) {
				execute();
			}
		} catch (Throwable e) {
			log.error(e);
		} finally {
	    	setStarted(false);
	    	setStopped(true);
	    	setClosed(true);
		}
	}
	
	protected void execute() throws Exception {
		//nothing by default
	}

	public void join() throws Exception {
		if (_thread != null) {
			_thread.join();
		}
	}

	public void reset() throws Exception {
		//nothing by default
	}
	
    public void stop() throws Exception {
    	if (!isStarted())
    		return;
    	if (isClosed())
    		throw new Exception("Already closed");
    	setStopping(true);
    	synchronized (_mutex) {
			try {
		    	doStop();
			} finally {
		    	setStarted(false);
		    	setStopping(false);
		    	setStopped(true);
		    	if (log.isTraceEnabled())
		    		log.trace("Stopped");
			}
	    }
	}
	
	protected void doStop() {
		//nothing by default
	}

	public void close() throws Exception {
    	setClosing(true);
		synchronized(_mutex) {
			try {
				doClose();
			} finally {
		    	setClosing(false);
		    	setClosed(true);
		    	if (log.isTraceEnabled())
		    		log.trace("Closed");
			}
		}
	}

	protected void doClose() {
		//nothing by default
	}

}
