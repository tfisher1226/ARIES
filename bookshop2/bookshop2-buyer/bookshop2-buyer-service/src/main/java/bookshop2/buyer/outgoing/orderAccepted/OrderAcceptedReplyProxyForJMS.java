package bookshop2.buyer.outgoing.orderAccepted;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.aries.bean.Proxy;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.OrderAcceptedMessage;
import bookshop2.buyer.BuyerContext;


public class OrderAcceptedReplyProxyForJMS extends JMSProxy implements Proxy<OrderAcceptedReply> {
	
	private OrderAcceptedReplyInterceptor orderAcceptedInterceptor;
	
	
	public OrderAcceptedReplyProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		orderAcceptedInterceptor = new OrderAcceptedReplyInterceptor();
		orderAcceptedInterceptor.setProxy(this);
	}
	
	@Override
	public OrderAcceptedReply getDelegate() {
		return orderAcceptedInterceptor;
	}
	
	@Override
	public void send(Serializable message) throws NamingException, JMSException {
		try {
			OrderAcceptedMessage orderAcceptedMessage = MessageUtil.getPart(message, "response");
			Object replyToDestination = BuyerContext.getReplyToDestination(orderAcceptedMessage, "OrderAccepted");
			send(replyToDestination, orderAcceptedMessage);
			log.info("#### [buyer]: OrderAccepted response sent");
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
