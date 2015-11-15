package admin.client.userService;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class UserServiceProxyForJMS extends JMSProxy implements Proxy<UserService> {
	
	private UserServiceInterceptor userServiceInterceptor;
	
	
	public UserServiceProxyForJMS(String serviceId) {
		super(serviceId);
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
