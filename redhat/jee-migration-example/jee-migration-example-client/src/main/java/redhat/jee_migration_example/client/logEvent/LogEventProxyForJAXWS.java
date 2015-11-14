package redhat.jee_migration_example.client.logEvent;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class LogEventProxyForJAXWS extends JAXWSProxy implements Proxy<LogEvent> {
	
	private LogEventService service;
	
	private Object mutex = new Object();
	
	
	public LogEventProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/jee-migration-example-service/logEventService/logEvent?wsdl";
	}
	
	@Override
	public LogEvent getDelegate() {
		return getPort();
	}
	
	protected LogEvent getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			LogEvent port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected LogEventService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			LogEventService service = new LogEventService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
