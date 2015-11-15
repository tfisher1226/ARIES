package bookshop2.seller.outgoing.orderRejected;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.aries.bean.Proxy;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.OrderRejectedMessage;


public class OrderRejectedReplyProxyForJMS extends JMSProxy implements Proxy<OrderRejectedReply> {

	private OrderRejectedReplyInterceptor orderRejectedReplyInterceptor;


	public OrderRejectedReplyProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}

	
	protected void createDelegate() {
		orderRejectedReplyInterceptor = new OrderRejectedReplyInterceptor();
		orderRejectedReplyInterceptor.setProxy(this);
	}
	
	@Override
	public OrderRejectedReply getDelegate() {
		return orderRejectedReplyInterceptor;
	}

	@Override
	public void send(Serializable message) throws NamingException, JMSException {
		try {
			OrderRejectedMessage orderRejectedMessage = MessageUtil.getPart(message, "orderRejectedMessage");
			String replyToDestination = getReplyToDestination(orderRejectedMessage, "OrderRejected");
			send(replyToDestination, orderRejectedMessage);
			log.info("#### [seller]: OrderRejected response sent");
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
