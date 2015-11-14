package redhat.jee_migration_example.client.logEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Event;


public class LogEventClient extends AbstractDelegate implements LogEvent {
	
	private static final Log log = LogFactory.getLog(LogEventClient.class);
	
	
	@Override
	public String getDomain() {
		return "redhat";
	}
	
	@Override
	public String getServiceId() {
		return LogEvent.ID;
	}
	
	@SuppressWarnings("unchecked")
	public LogEvent getProxy() throws Exception {
		return getProxy(LogEvent.ID);
	}
	
	@Override
	public void logEvent(Event event) {
		try {
			getProxy().logEvent(event);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
