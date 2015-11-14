package redhat.jee_migration_example.client.populateCatalog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;


public class PopulateCatalogClient extends AbstractDelegate implements PopulateCatalog {
	
	private static final Log log = LogFactory.getLog(PopulateCatalogClient.class);
	
	
	@Override
	public String getDomain() {
		return "redhat";
	}
	
	@Override
	public String getServiceId() {
		return PopulateCatalog.ID;
	}
	
	@SuppressWarnings("unchecked")
	public PopulateCatalog getProxy() throws Exception {
		return getProxy(PopulateCatalog.ID);
	}
	
	@Override
	public void populateCatalog() {
		try {
			getProxy().populateCatalog();
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
