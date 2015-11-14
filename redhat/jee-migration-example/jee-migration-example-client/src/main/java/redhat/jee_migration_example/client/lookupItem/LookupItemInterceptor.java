package redhat.jee_migration_example.client.lookupItem;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;


@SuppressWarnings("serial")
public class LookupItemInterceptor extends MessageInterceptor<LookupItem> implements LookupItem {
	
	@Override
	public void lookupItem(Long id) {
		try {
			log.info("#### [eventLogger-client]: lookupItem() sending");
			Message request = createMessage("lookupItem");
			request.addPart("id", id);
			getProxy().send(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
