package redhat.jee_migration_example.client.logEvent;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Event;


public class LogEventProxyForJMS extends JMSProxy implements Proxy<LogEvent> {
	
	private static final String DESTINATION = "/queue/public_redhat_log_event_queue";
	
	private LogEventInterceptor logEventInterceptor;
	
	
	public LogEventProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		logEventInterceptor = new LogEventInterceptor();
		logEventInterceptor.setProxy(this);
	}
	
	@Override
	public LogEvent getDelegate() {
		return logEventInterceptor;
	}
	
	public void logEvent(Event event) {
		//Check.isValid("event", event);
		try {
			send(event);
			log.info("#### [eventLogger-client]: LogEvent request sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
