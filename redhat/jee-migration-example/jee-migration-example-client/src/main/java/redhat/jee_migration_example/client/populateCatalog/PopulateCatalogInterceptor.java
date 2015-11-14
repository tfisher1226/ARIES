package redhat.jee_migration_example.client.populateCatalog;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;


@SuppressWarnings("serial")
public class PopulateCatalogInterceptor extends MessageInterceptor<PopulateCatalog> implements PopulateCatalog {
	
	@Override
	public void populateCatalog() {
		try {
			log.info("#### [eventLogger-client]: populateCatalog() sending");
			Message message = createMessage("populateCatalog");
			getProxy().send(message);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
