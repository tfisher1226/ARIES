package org.aries.tx.service.jms;

import java.util.Map;

import org.aries.Assert;
import org.aries.common.AbstractMessage;
import org.aries.jms.client.JmsClient;


public abstract class AbstractJMSClient extends JmsClient {

	protected String getReplyToDestination(AbstractMessage message, String serviceName) {
		Map<String, String> replyToDestinations = message.getReplyToDestinations();
//		if (replyToDestinations == null)
//			System.out.println();
		Assert.notNull(replyToDestinations, "ReplyTo map not found");
		String replyToDestination = replyToDestinations.get(serviceName);
//		if (replyToDestination == null)
//			System.out.println();
		Assert.notNull(replyToDestination, "ReplyTo destination not found: "+serviceName);
		String jndiName = "queue/" + replyToDestination;
		return jndiName;
	}
	
}
