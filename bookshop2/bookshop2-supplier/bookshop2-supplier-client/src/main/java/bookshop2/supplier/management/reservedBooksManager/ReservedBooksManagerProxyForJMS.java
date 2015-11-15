package bookshop2.supplier.management.reservedBooksManager;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;


public class ReservedBooksManagerProxyForJMS extends JMSProxy implements Proxy<ReservedBooksManager> {
	
	private ReservedBooksManagerInterceptor reservedBooksManagerInterceptor;
	
	
	public ReservedBooksManagerProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		reservedBooksManagerInterceptor = new ReservedBooksManagerInterceptor();
		reservedBooksManagerInterceptor.setProxy(this);
	}
	
	@Override
	public ReservedBooksManager getDelegate() {
		return reservedBooksManagerInterceptor;
	}
	
}
