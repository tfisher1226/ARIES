package redhat.jee_migration_example.client.populateCatalog;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;


public class PopulateCatalogProxyForJMS extends JMSProxy implements Proxy<PopulateCatalog> {
	
	private static final String DESTINATION = "/queue/public_redhat_populate_catalog_queue";
	
	private PopulateCatalogInterceptor populateCatalogInterceptor;
	
	
	public PopulateCatalogProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		populateCatalogInterceptor = new PopulateCatalogInterceptor();
		populateCatalogInterceptor.setProxy(this);
	}
	
	@Override
	public PopulateCatalog getDelegate() {
		return populateCatalogInterceptor;
	}
	
	public void populateCatalog() {
		try {
			send("");
			log.info("#### [eventLogger-client]: PopulateCatalog request sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
