package bookshop2.seller.outgoing.orderAccepted;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.aries.bean.Proxy;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.OrderAcceptedMessage;


public class OrderAcceptedReplyProxyForJMS extends JMSProxy implements Proxy<OrderAcceptedReply> {
	
	private OrderAcceptedReplyInterceptor orderAcceptedReplyInterceptor;
	
	
	public OrderAcceptedReplyProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		orderAcceptedReplyInterceptor = new OrderAcceptedReplyInterceptor();
		orderAcceptedReplyInterceptor.setProxy(this);
	}
	
	@Override
	public OrderAcceptedReply getDelegate() {
		return orderAcceptedReplyInterceptor;
	}

	@Override
	public void send(Serializable message) throws NamingException, JMSException {
		try {
			OrderAcceptedMessage orderAcceptedMessage = MessageUtil.getPart(message, "orderAcceptedMessage");
			String replyToDestination = getReplyToDestination(orderAcceptedMessage, "OrderAccepted");
			send(replyToDestination, orderAcceptedMessage);
			log.info("#### [seller]: OrderAccepted response sent");
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
