package redhat.jee_migration_example.client.populateCatalog;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class PopulateCatalogProxyForEJB extends EJBProxy implements Proxy<PopulateCatalog> {
	
	private PopulateCatalogInterceptor populateCatalogInterceptor;
	
	
	public PopulateCatalogProxyForEJB(String serviceId, String host, int port) {
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
