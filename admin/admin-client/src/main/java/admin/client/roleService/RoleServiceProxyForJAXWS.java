package admin.client.roleService;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class RoleServiceProxyForJAXWS extends JAXWSProxy implements Proxy<RoleService> {
	
	private RoleServiceService service;
	
	private Object mutex = new Object();
	
	
	public RoleServiceProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/admin-service/roleServiceService/roleService?wsdl";
	}
	
	@Override
	public RoleService getDelegate() {
		return getPort();
	}
	
	protected RoleService getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			RoleService port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected RoleServiceService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			RoleServiceService service = new RoleServiceService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
