package admin.client.registrationService;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class RegistrationServiceProxyForJMS extends JMSProxy implements Proxy<RegistrationService> {
	
	private RegistrationServiceInterceptor registrationServiceInterceptor;
	
	
	public RegistrationServiceProxyForJMS(String serviceId) {
		super(serviceId);
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
