package redhat.jee_migration_example.client.populateCatalog;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class PopulateCatalogProxyForJAXWS extends JAXWSProxy implements Proxy<PopulateCatalog> {
	
	private PopulateCatalogService service;
	
	private Object mutex = new Object();
	
	
	public PopulateCatalogProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/jee-migration-example-service/populateCatalogService/populateCatalog?wsdl";
	}
	
	@Override
	public PopulateCatalog getDelegate() {
		return getPort();
	}
	
	protected PopulateCatalog getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			PopulateCatalog port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected PopulateCatalogService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			PopulateCatalogService service = new PopulateCatalogService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
