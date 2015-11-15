package bookshop2.seller.outgoing.purchaseRejected;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.aries.bean.Proxy;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.PurchaseRejectedMessage;


public class PurchaseRejectedReplyProxyForJMS extends JMSProxy implements Proxy<PurchaseRejectedReply> {

	private PurchaseRejectedReplyInterceptor purchaseRejectedReplyInterceptor;


	public PurchaseRejectedReplyProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}

	
	protected void createDelegate() {
		purchaseRejectedReplyInterceptor = new PurchaseRejectedReplyInterceptor();
		purchaseRejectedReplyInterceptor.setProxy(this);
		}
	
	@Override
	public PurchaseRejectedReply getDelegate() {
		return purchaseRejectedReplyInterceptor;
	}

	@Override
	public void send(Serializable message) throws NamingException, JMSException {
		try {
			PurchaseRejectedMessage purchaseRejectedMessage = MessageUtil.getPart(message, "purchaseRejectedMessage");
			String replyToDestination = getReplyToDestination(purchaseRejectedMessage, "PurchaseRejected");
			send(replyToDestination, purchaseRejectedMessage);
			log.info("#### [seller]: PurchaseRejected response sent");
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
