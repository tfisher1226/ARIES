package org.aries.action;

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Action;
import org.aries.message.Message;


public abstract class AbstractAction implements Action {

	protected final Log log = LogFactory.getLog(getClass());

	private final ReentrantReadWriteLock _lock;
	
	protected Map<String, String> _properties;

	private String _name;
	
	private String _description;

	
	public AbstractAction() {
		_lock = new ReentrantReadWriteLock();
		_description = getClass().getName();
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getProperty(String name) {
		return _properties.get(name);
	}

	public void setProperty(String name, String value) {
		_properties.put(name, value);
	}

	public void setProperties(Map<String, String> values) {
		this._properties = values;
	}

	protected void grabWriteLock() {
		_lock.writeLock().lock();
	}

	protected void releaseWriteLock() {
		_lock.writeLock().unlock();
	}

	protected void grabReadLock() {
		_lock.readLock().lock();
	}

	protected void releaseReadLock() {
		_lock.readLock().unlock();
	}

	public void setOffline() {
		grabWriteLock();
	}

	public void setOnline() {
		releaseWriteLock();
	}


	//TODO @Create
	public void initialize() throws Exception {
		//NOT YET Runtime.getRuntime().addShutdownHook(createShutdownHook());
	}

	//TODO @Destroy
	public void destroy() throws Exception {
		//NOT YET grabWriteLock();
	}

	protected Thread createShutdownHook() {
		return new Thread() {
			public void run() {
				try {
					AbstractAction.this.destroy();
				} catch (Exception e) {
					//ignore
				}
			}
		};
	}

	
	public void logStarted() {
		logStateChange("started");
	}

	public void logStarted(String message) {
		logStateChange("started - "+message);
	}

	public void logAborted() {
		logStateChange("aborted");
	}

	public void logAborted(String message) {
		logStateChange("aborted - "+message);
	}

	public void logAborted(Throwable exception) {
		logStateChange(exception);
	}

	public void logComplete() {
		logStateChange("complete");
	}

	public void logComplete(String message) {
		logStateChange("complete - "+message);
	}

	public void logEvent(String message) {
		logStateChange(message);
	}

//	public void logEvent(Event event) {
//		logStateChange(event);
//	}

	public void logException(Throwable exception) {
		logStateChange(exception);
	}

	void logStateChange(String stateName) {
		//TODO validate current transaction context exists
		//TODO perform as participant within current transaction context 
		//TODO add work unit state information into shared command stack
		log.info(getDescription()+": "+stateName);
	}

//	void logStateChange(Event event) {
//		//TODO map event to work unit state information  
//		//TODO implement this - make sure event info gets logged 
//		log.info(getDescription()+": "+event.getMessage());
//	}

	void logStateChange(Throwable exception) {
		//TODO map event to work unit state information  
		//TODO implement this - make sure event info gets logged 
		log.error(getDescription()+": "+exception.getMessage(), exception);
	}
	

//	protected <T> T getBeanInstance(String beanName) {
//		try {
//			@SuppressWarnings("unchecked") T instance = (T) BeanContext.get(beanName);
//			return instance;
//		} catch (Throwable e) {
//			//hide spring exceptions from invoking layers
//			throw new BeanNotFoundException(e.getMessage());
//		}
//	}

	
	public Message process(Message message) throws Exception {
		//do nothing by default
		return message;
	}
}
