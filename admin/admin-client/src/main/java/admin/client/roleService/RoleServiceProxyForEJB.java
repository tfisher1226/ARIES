package admin.client.roleService;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class RoleServiceProxyForEJB extends EJBProxy implements Proxy<RoleService> {
	
	private RoleServiceInterceptor roleServiceInterceptor;
	
	
	public RoleServiceProxyForEJB(String serviceId, String host, int port) {
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
