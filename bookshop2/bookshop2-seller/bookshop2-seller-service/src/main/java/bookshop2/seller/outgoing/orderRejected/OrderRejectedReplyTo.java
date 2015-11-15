package bookshop2.seller.outgoing.orderRejected;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.OrderRejectedMessage;


public class OrderRejectedReplyTo extends AbstractDelegate implements OrderRejectedReply {
	
	private static final Log log = LogFactory.getLog(OrderRejectedReplyTo.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2.seller";
	}
	
	@Override
	public String getServiceId() {
		return OrderRejectedReply.ID;
	}
	
	@SuppressWarnings("unchecked")
	public OrderRejectedReply getProxy() throws Exception {
		return getProxy(OrderRejectedReply.ID);
	}
	
	@Override
	public void orderRejected(OrderRejectedMessage orderRejectedMessage) {
		try {
			getProxy().orderRejected(orderRejectedMessage);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
