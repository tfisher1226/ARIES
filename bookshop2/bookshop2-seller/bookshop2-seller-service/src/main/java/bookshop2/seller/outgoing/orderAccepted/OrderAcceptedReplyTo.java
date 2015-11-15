package bookshop2.seller.outgoing.orderAccepted;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.OrderAcceptedMessage;


public class OrderAcceptedReplyTo extends AbstractDelegate implements OrderAcceptedReply {
	
	private static final Log log = LogFactory.getLog(OrderAcceptedReplyTo.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2.seller";
	}
	
	@Override
	public String getServiceId() {
		return OrderAcceptedReply.ID;
	}
	
	@SuppressWarnings("unchecked")
	public OrderAcceptedReply getProxy() throws Exception {
		return getProxy(OrderAcceptedReply.ID);
	}
	
	@Override
	public void orderAccepted(OrderAcceptedMessage orderAcceptedMessage) {
		try {
			getProxy().orderAccepted(orderAcceptedMessage);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
