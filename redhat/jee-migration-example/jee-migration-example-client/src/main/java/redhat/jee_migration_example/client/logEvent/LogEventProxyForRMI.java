package redhat.jee_migration_example.client.logEvent;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class LogEventProxyForRMI extends RMIProxy implements Proxy<LogEvent> {
	
	private LogEventInterceptor logEventInterceptor;
	
	
	public LogEventProxyForRMI(String serviceId, String host, int port) {
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
