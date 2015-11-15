package admin.client.registrationService;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class RegistrationServiceProxyForRMI extends RMIProxy implements Proxy<RegistrationService> {
	
	private RegistrationServiceInterceptor registrationServiceInterceptor;
	
	
	public RegistrationServiceProxyForRMI(String serviceId, String host, int port) {
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
