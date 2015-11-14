package redhat.jee_migration_example.client.lookupItem;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class LookupItemProxyForRMI extends RMIProxy implements Proxy<LookupItem> {
	
	private LookupItemInterceptor lookupItemInterceptor;
	
	
	public LookupItemProxyForRMI(String serviceId, String host, int port) {
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
