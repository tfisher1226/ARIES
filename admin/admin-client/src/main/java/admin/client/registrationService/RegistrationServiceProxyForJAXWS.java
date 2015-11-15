package admin.client.registrationService;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class RegistrationServiceProxyForJAXWS extends JAXWSProxy implements Proxy<RegistrationService> {
	
	private RegistrationServiceService service;
	
	private Object mutex = new Object();
	
	
	public RegistrationServiceProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/admin-service/registrationServiceService/registrationService?wsdl";
	}
	
	@Override
	public RegistrationService getDelegate() {
		return getPort();
	}
	
	protected RegistrationService getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			RegistrationService port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected RegistrationServiceService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			RegistrationServiceService service = new RegistrationServiceService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
