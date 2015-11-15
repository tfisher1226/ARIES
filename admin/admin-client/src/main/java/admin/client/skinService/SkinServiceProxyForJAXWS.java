package admin.client.skinService;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class SkinServiceProxyForJAXWS extends JAXWSProxy implements Proxy<SkinService> {
	
	private SkinServiceService service;
	
	private Object mutex = new Object();
	
	
	public SkinServiceProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/admin-service/skinServiceService/skinService?wsdl";
	}
	
	@Override
	public SkinService getDelegate() {
		return getPort();
	}
	
	protected SkinService getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			SkinService port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected SkinServiceService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			SkinServiceService service = new SkinServiceService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
