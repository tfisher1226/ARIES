package bookshop2.seller.outgoing.purchaseAccepted;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.aries.bean.Proxy;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.PurchaseAcceptedMessage;


public class PurchaseAcceptedReplyProxyForJMS extends JMSProxy implements Proxy<PurchaseAcceptedReply> {

	private PurchaseAcceptedReplyInterceptor purchaseAcceptedReplyInterceptor;


	public PurchaseAcceptedReplyProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}

	
	protected void createDelegate() {
		purchaseAcceptedReplyInterceptor = new PurchaseAcceptedReplyInterceptor();
		purchaseAcceptedReplyInterceptor.setProxy(this);
	}
	
	@Override
	public PurchaseAcceptedReply getDelegate() {
		return purchaseAcceptedReplyInterceptor;
	}

	@Override
	public void send(Serializable message) throws NamingException, JMSException {
		try {
			PurchaseAcceptedMessage purchaseAcceptedMessage = MessageUtil.getPart(message, "purchaseAcceptedMessage");
			String replyToDestination = getReplyToDestination(purchaseAcceptedMessage, "PurchaseAccepted");
			send(replyToDestination, purchaseAcceptedMessage);
			log.info("#### [seller]: PurchaseAccepted response sent");
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
