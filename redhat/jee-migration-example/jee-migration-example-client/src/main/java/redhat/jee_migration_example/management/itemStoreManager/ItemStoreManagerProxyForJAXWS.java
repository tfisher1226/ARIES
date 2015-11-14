package redhat.jee_migration_example.management.itemStoreManager;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class ItemStoreManagerProxyForJAXWS extends JAXWSProxy implements Proxy<ItemStoreManager> {
	
	private ItemStoreManagerService service;
	
	private Object mutex = new Object();
	
	
	public ItemStoreManagerProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/jee-migration-example-service/itemStoreManagerService/itemStoreManager?wsdl";
	}
	
	@Override
	public ItemStoreManager getDelegate() {
		return getPort();
	}
	
	protected ItemStoreManager getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			ItemStoreManager port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected ItemStoreManagerService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			ItemStoreManagerService service = new ItemStoreManagerService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
