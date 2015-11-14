package redhat.jee_migration_example.client.logEvent;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class LogEventProxyForEJB extends EJBProxy implements Proxy<LogEvent> {
	
	private LogEventInterceptor logEventInterceptor;
	
	
	public LogEventProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
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
	
}
