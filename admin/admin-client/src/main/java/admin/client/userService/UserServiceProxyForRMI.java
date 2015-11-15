package admin.client.userService;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class UserServiceProxyForRMI extends RMIProxy implements Proxy<UserService> {
	
	private UserServiceInterceptor userServiceInterceptor;
	
	
	public UserServiceProxyForRMI(String serviceId, String host, int port) {
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
