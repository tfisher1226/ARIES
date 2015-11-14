package redhat.jee_migration_example.client.lookupItem;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class LookupItemProxyForJAXWS extends JAXWSProxy implements Proxy<LookupItem> {
	
	private LookupItemService service;
	
	private Object mutex = new Object();
	
	
	public LookupItemProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/jee-migration-example-service/lookupItemService/lookupItem?wsdl";
	}
	
	@Override
	public LookupItem getDelegate() {
		return getPort();
	}
	
	protected LookupItem getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			LookupItem port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected LookupItemService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			LookupItemService service = new LookupItemService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
