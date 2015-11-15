package admin.client.skinService;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class SkinServiceProxyForRMI extends RMIProxy implements Proxy<SkinService> {
	
	private SkinServiceInterceptor skinServiceInterceptor;
	
	
	public SkinServiceProxyForRMI(String serviceId, String host, int port) {
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
