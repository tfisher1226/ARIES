package bookshop2.supplier.outgoing.shipmentScheduled;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.aries.bean.Proxy;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentScheduledMessage;


public class ShipmentScheduledReplyProxyForJMS extends JMSProxy implements Proxy<ShipmentScheduledReply> {
	
	private ShipmentScheduledReplyInterceptor shipmentScheduledReplyInterceptor;
	
	
	public ShipmentScheduledReplyProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		shipmentScheduledReplyInterceptor = new ShipmentScheduledReplyInterceptor();
		shipmentScheduledReplyInterceptor.setProxy(this);
	}
	
	@Override
	public ShipmentScheduledReply getDelegate() {
		return shipmentScheduledReplyInterceptor;
	}
	
	@Override
	public void send(Serializable message) throws NamingException, JMSException {
		try {
			ShipmentScheduledMessage shipmentScheduledMessage = MessageUtil.getPart(message, "shipmentScheduledMessage");
			String replyToDestination = getReplyToDestination(shipmentScheduledMessage, "ShipmentScheduled");
			send(replyToDestination, shipmentScheduledMessage);
			log.info("#### [supplier]: ShipmentScheduled response sent");
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
