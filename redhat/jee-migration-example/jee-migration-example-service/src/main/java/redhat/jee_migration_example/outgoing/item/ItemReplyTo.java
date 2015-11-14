package redhat.jee_migration_example.outgoing.item;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Item;


public class ItemReplyTo extends AbstractDelegate implements ItemReply {
	
	private static final Log log = LogFactory.getLog(ItemReplyTo.class);
	
	
	@Override
	public String getDomain() {
		return "redhat";
	}
	
	@Override
	public String getServiceId() {
		return ItemReply.ID;
	}
	
	@SuppressWarnings("unchecked")
	public ItemReply getProxy() throws Exception {
		return getProxy(ItemReply.ID);
	}
	
	@Override
	public void replyItem(Item item) {
		try {
			getProxy().replyItem(item);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
