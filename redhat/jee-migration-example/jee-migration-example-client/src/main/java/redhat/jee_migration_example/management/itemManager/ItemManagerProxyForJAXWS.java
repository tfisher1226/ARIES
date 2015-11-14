package redhat.jee_migration_example.management.itemManager;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class ItemManagerProxyForJAXWS extends JAXWSProxy implements Proxy<ItemManager> {
	
	private ItemManagerService service;
	
	private Object mutex = new Object();
	
	
	public ItemManagerProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/jee-migration-example-service/itemManagerService/itemManager?wsdl";
	}
	
	@Override
	public ItemManager getDelegate() {
		return getPort();
	}
	
	protected ItemManager getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			ItemManager port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected ItemManagerService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			ItemManagerService service = new ItemManagerService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
