package admin.client.skinService;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class SkinServiceProxyForEJB extends EJBProxy implements Proxy<SkinService> {
	
	private SkinServiceInterceptor skinServiceInterceptor;
	
	
	public SkinServiceProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
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
