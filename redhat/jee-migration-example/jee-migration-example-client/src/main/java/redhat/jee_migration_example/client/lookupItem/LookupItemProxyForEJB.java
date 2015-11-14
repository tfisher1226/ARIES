package redhat.jee_migration_example.client.lookupItem;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class LookupItemProxyForEJB extends EJBProxy implements Proxy<LookupItem> {
	
	private LookupItemInterceptor lookupItemInterceptor;
	
	
	public LookupItemProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		lookupItemInterceptor = new LookupItemInterceptor();
		lookupItemInterceptor.setProxy(this);
	}
	
	@Override
	public LookupItem getDelegate() {
		return lookupItemInterceptor;
	}
	
}
