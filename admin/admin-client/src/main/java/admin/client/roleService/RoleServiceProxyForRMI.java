package admin.client.roleService;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class RoleServiceProxyForRMI extends RMIProxy implements Proxy<RoleService> {
	
	private RoleServiceInterceptor roleServiceInterceptor;
	
	
	public RoleServiceProxyForRMI(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		roleServiceInterceptor = new RoleServiceInterceptor();
		roleServiceInterceptor.setProxy(this);
	}
	
	@Override
	public RoleService getDelegate() {
		return roleServiceInterceptor;
	}
	
}
