package bookshop2.supplier.outgoing.shipmentComplete;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.aries.bean.Proxy;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentCompleteMessage;


public class ShipmentCompleteReplyProxyForJMS extends JMSProxy implements Proxy<ShipmentCompleteReply> {
	
	private ShipmentCompleteReplyInterceptor shipmentCompleteReplyInterceptor;
	
	
	public ShipmentCompleteReplyProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		shipmentCompleteReplyInterceptor = new ShipmentCompleteReplyInterceptor();
		shipmentCompleteReplyInterceptor.setProxy(this);
	}
	
	@Override
	public ShipmentCompleteReply getDelegate() {
		return shipmentCompleteReplyInterceptor;
	}
	
	@Override
	public void send(Serializable message) throws NamingException, JMSException {
		try {
			ShipmentCompleteMessage shipmentCompleteMessage = MessageUtil.getPart(message, "shipmentCompleteMessage");
			String replyToDestination = getReplyToDestination(shipmentCompleteMessage, "ShipmentComplete");
			send(replyToDestination, shipmentCompleteMessage);
			log.info("#### [supplier]: ShipmentComplete response sent");
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
