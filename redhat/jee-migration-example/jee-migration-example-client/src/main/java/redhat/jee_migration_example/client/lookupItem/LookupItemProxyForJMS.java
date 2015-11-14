package redhat.jee_migration_example.client.lookupItem;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;


public class LookupItemProxyForJMS extends JMSProxy implements Proxy<LookupItem> {
	
	private static final String DESTINATION = "/queue/public_redhat_lookup_item_queue";
	
	private LookupItemInterceptor lookupItemInterceptor;
	
	
	public LookupItemProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		lookupItemInterceptor = new LookupItemInterceptor();
		lookupItemInterceptor.setProxy(this);
	}
	
	@Override
	public LookupItem getDelegate() {
		return lookupItemInterceptor;
	}
	
	public void lookupItem(Long id) {
		//Check.isValid("id", id);
		try {
			invoke(id);
			log.info("#### [eventLogger-client]: LookupItem request sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
