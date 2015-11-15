package bookshop2.shipper.outgoing.shipmentFailed;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.aries.bean.Proxy;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentFailedMessage;


public class ShipmentFailedReplyProxyForJMS extends JMSProxy implements Proxy<ShipmentFailedReply> {
	
	private ShipmentFailedReplyInterceptor shipmentFailedReplyInterceptor;
	
	
	public ShipmentFailedReplyProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		shipmentFailedReplyInterceptor = new ShipmentFailedReplyInterceptor();
		shipmentFailedReplyInterceptor.setProxy(this);
	}
	
	@Override
	public ShipmentFailedReply getDelegate() {
		return shipmentFailedReplyInterceptor;
	}
	
	@Override
	public void send(Serializable message) throws NamingException, JMSException {
		try {
			ShipmentFailedMessage shipmentFailedMessage = MessageUtil.getPart(message, "shipmentFailedMessage");
			String replyToDestination = getReplyToDestination(shipmentFailedMessage, "ShipmentFailed");
			send(replyToDestination, shipmentFailedMessage);
			log.info("#### [shipper]: ShipmentFailed response sent");
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
