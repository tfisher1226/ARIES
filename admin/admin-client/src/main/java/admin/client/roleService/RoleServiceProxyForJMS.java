package admin.client.roleService;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class RoleServiceProxyForJMS extends JMSProxy implements Proxy<RoleService> {
	
	private RoleServiceInterceptor roleServiceInterceptor;
	
	
	public RoleServiceProxyForJMS(String serviceId) {
		super(serviceId);
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
