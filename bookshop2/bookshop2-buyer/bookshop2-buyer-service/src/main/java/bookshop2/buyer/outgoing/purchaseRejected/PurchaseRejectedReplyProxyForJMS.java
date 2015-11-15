package bookshop2.buyer.outgoing.purchaseRejected;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.aries.bean.Proxy;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.PurchaseRejectedMessage;


public class PurchaseRejectedReplyProxyForJMS extends JMSProxy implements Proxy<PurchaseRejectedReply> {
	
	private PurchaseRejectedReplyInterceptor purchaseRejectedInterceptor;
	
	
	public PurchaseRejectedReplyProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		purchaseRejectedInterceptor = new PurchaseRejectedReplyInterceptor();
		purchaseRejectedInterceptor.setProxy(this);
	}
	
	@Override
	public PurchaseRejectedReply getDelegate() {
		return purchaseRejectedInterceptor;
	}
	
	@Override
	public void send(Serializable message) throws NamingException, JMSException {
		try {
			PurchaseRejectedMessage purchaseRejectedMessage = MessageUtil.getPart(message, "purchaseRejectedMessage");
			String replyToDestination = getReplyToDestination(purchaseRejectedMessage, "PurchaseRejected");
			send(replyToDestination, purchaseRejectedMessage);
			log.info("#### [buyer]: PurchaseRejected response sent");
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
