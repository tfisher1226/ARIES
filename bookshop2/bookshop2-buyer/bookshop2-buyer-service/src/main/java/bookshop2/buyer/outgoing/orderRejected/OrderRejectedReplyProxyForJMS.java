package bookshop2.buyer.outgoing.orderRejected;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.aries.bean.Proxy;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.OrderRejectedMessage;
import bookshop2.buyer.BuyerContext;
import bookshop2.buyer.incoming.orderBooks.OrderRejected;


public class OrderRejectedReplyProxyForJMS extends JMSProxy implements Proxy<OrderRejectedReply> {
	
	private OrderRejectedReplyInterceptor orderRejectedInterceptor;
	
	
	public OrderRejectedReplyProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		orderRejectedInterceptor = new OrderRejectedReplyInterceptor();
		orderRejectedInterceptor.setProxy(this);
	}
	
	@Override
	public OrderRejectedReply getDelegate() {
		return orderRejectedInterceptor;
	}
	
	@Override
	public void send(Serializable message) throws NamingException, JMSException {
		try {
			OrderRejectedMessage orderRejectedMessage = MessageUtil.getPart(message, "response");
			Object replyToDestination = BuyerContext.getReplyToDestination(orderRejectedMessage, "OrderRejected");
			//Object replyToDestination = getReplyTo(orderRejectedMessage, "OrderRejected");
			send(replyToDestination, orderRejectedMessage);
			log.info("#### [buyer]: OrderRejected response sent");
			
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

}
