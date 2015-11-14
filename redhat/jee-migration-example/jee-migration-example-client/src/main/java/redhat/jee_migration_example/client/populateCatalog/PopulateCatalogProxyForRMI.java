package redhat.jee_migration_example.client.populateCatalog;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class PopulateCatalogProxyForRMI extends RMIProxy implements Proxy<PopulateCatalog> {
	
	private PopulateCatalogInterceptor populateCatalogInterceptor;
	
	
	public PopulateCatalogProxyForRMI(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		populateCatalogInterceptor = new PopulateCatalogInterceptor();
		populateCatalogInterceptor.setProxy(this);
	}
	
	@Override
	public PopulateCatalog getDelegate() {
		return populateCatalogInterceptor;
	}
	
}
