package redhat.jee_migration_example.management.itemStoreManager;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class ItemStoreManagerProxyForEJB extends EJBProxy implements Proxy<ItemStoreManager> {
	
	private ItemStoreManagerInterceptor itemStoreManagerInterceptor;
	
	
	public ItemStoreManagerProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		itemStoreManagerInterceptor = new ItemStoreManagerInterceptor();
		itemStoreManagerInterceptor.setProxy(this);
	}
	
	@Override
	public ItemStoreManager getDelegate() {
		return itemStoreManagerInterceptor;
	}
	
}
