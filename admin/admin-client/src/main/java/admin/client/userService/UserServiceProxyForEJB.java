package admin.client.userService;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class UserServiceProxyForEJB extends EJBProxy implements Proxy<UserService> {
	
	private UserServiceInterceptor userServiceInterceptor;
	
	
	public UserServiceProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		userServiceInterceptor = new UserServiceInterceptor();
		userServiceInterceptor.setProxy(this);
	}
	
	@Override
	public UserService getDelegate() {
		return userServiceInterceptor;
	}
	
}
