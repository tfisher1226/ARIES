package admin.client.userService;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class UserServiceProxyForJAXWS extends JAXWSProxy implements Proxy<UserService> {
	
	private UserServiceService service;
	
	private Object mutex = new Object();
	
	
	public UserServiceProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/admin-service/userServiceService/userService?wsdl";
	}
	
	@Override
	public UserService getDelegate() {
		return getPort();
	}
	
	protected UserService getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			UserService port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected UserServiceService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			UserServiceService service = new UserServiceService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
