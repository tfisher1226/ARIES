package redhat.jee_migration_example.management.itemManager;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class ItemManagerProxyForRMI extends RMIProxy implements Proxy<ItemManager> {
	
	private ItemManagerInterceptor itemManagerInterceptor;
	
	
	public ItemManagerProxyForRMI(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		itemManagerInterceptor = new ItemManagerInterceptor();
		itemManagerInterceptor.setProxy(this);
	}
	
	@Override
	public ItemManager getDelegate() {
		return itemManagerInterceptor;
	}
	
}
