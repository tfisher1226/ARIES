package redhat.jee_migration_example.client.lookupItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;


public class LookupItemClient extends AbstractDelegate implements LookupItem {
	
	private static final Log log = LogFactory.getLog(LookupItemClient.class);
	
	
	@Override
	public String getDomain() {
		return "redhat";
	}
	
	@Override
	public String getServiceId() {
		return LookupItem.ID;
	}
	
	@SuppressWarnings("unchecked")
	public LookupItem getProxy() throws Exception {
		return getProxy(LookupItem.ID);
	}
	
	@Override
	public void lookupItem(Long id) {
		try {
			getProxy().lookupItem(id);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
