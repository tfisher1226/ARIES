package redhat.jee_migration_example.outgoing.item;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Item;


@SuppressWarnings("serial")
public class ItemReplyInterceptor extends MessageInterceptor<ItemReply> implements ItemReply {
	
	@Override
	public void replyItem(Item item) {
		try {
			log.info("#### [eventLogger]: replyItem() sending");
			Message message = createMessage("replyItem");
			message.addPart("item", item);
			getProxy().send(message);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
