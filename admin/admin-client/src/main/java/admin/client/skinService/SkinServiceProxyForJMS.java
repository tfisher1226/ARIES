package admin.client.skinService;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class SkinServiceProxyForJMS extends JMSProxy implements Proxy<SkinService> {
	
	private SkinServiceInterceptor skinServiceInterceptor;
	
	
	public SkinServiceProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}

	
	protected void createDelegate() {
		skinServiceInterceptor = new SkinServiceInterceptor();
		skinServiceInterceptor.setProxy(this);
	}

	@Override
	public SkinService getDelegate() {
		return skinServiceInterceptor;
	}

}
