package redhat.jee_migration_example.client.logEvent;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "logEvent", targetNamespace = "http://www.redhat.com/jee-migration-example")
public class LogEventService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private LogEventService service;
	
	
	public LogEventService() {
		this(WSDL_LOCATION);
	}
	
	public LogEventService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://www.redhat.com/jee-migration-example", "logEventService"));
	}
	
	public LogEventService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "logEvent")
	public LogEvent getPort() {
		return super.getPort(new QName("http://www.redhat.com/jee-migration-example", "logEvent"), LogEvent.class);
	}
	
	@WebEndpoint(name = "logEvent")
	public LogEvent getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://www.redhat.com/jee-migration-example", "logEvent"), LogEvent.class, features);
	}
	
}
