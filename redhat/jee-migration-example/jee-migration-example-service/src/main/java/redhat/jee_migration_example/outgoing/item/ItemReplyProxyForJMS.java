package redhat.jee_migration_example.outgoing.item;

import java.io.Serializable;
import java.util.Map;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.aries.Assert;
import org.aries.bean.Proxy;
import org.aries.jms.util.MessageUtil;
import org.aries.runtime.BeanContext;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.EventLoggerContext;
import redhat.jee_migration_example.Item;


public class ItemReplyProxyForJMS extends JMSProxy implements Proxy<ItemReply> {
	
	private static final String DESTINATION = "/queue/public_redhat_item_queue";
	
	private ItemReplyInterceptor itemInterceptor;
	
	
	public ItemReplyProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		itemInterceptor = new ItemReplyInterceptor();
		itemInterceptor.setProxy(this);
	}
	
	@Override
	public ItemReply getDelegate() {
		return itemInterceptor;
	}
	
//	protected String getReplyToDestination(String serviceName) {
//		Map<String, String> replyToDestinations = message.getReplyToDestinations();
//		Assert.notNull(replyToDestinations, "ReplyTo map not found");
//		String replyToDestination = replyToDestinations.get(serviceName);
//		Assert.notNull(replyToDestination, "ReplyTo destination not found: "+serviceName);
//		String jndiName = "queue/" + replyToDestination;
//		return jndiName;
//	}
	
	public void send_item_reply(Item item) {
		try {
			EventLoggerContext eventLoggerContext = BeanContext.getFromConversation("eventLoggerContext");
			String replyTo = eventLoggerContext.getReplyTo();
			send(replyTo, item);
			log.info("#### [eventLogger]: Item response sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void send(Serializable message) throws NamingException, JMSException {
		try {
			Item item = MessageUtil.getPart(message, "item");
			EventLoggerContext eventLoggerContext = BeanContext.getFromConversation("eventLoggerContext");
			String replyTo = eventLoggerContext.getReplyTo();
			send(replyTo, item);
			log.info("#### [eventLogger]: Item response sent");
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
