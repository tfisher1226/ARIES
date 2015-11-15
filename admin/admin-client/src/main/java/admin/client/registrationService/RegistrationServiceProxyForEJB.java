package admin.client.registrationService;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class RegistrationServiceProxyForEJB extends EJBProxy implements Proxy<RegistrationService> {
	
	private RegistrationServiceInterceptor registrationServiceInterceptor;
	
	
	public RegistrationServiceProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		registrationServiceInterceptor = new RegistrationServiceInterceptor();
		registrationServiceInterceptor.setProxy(this);
	}
	
	@Override
	public RegistrationService getDelegate() {
		return registrationServiceInterceptor;
	}
	
}
