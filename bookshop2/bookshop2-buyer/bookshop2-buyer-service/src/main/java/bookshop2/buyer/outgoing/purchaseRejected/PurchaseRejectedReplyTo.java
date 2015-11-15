package bookshop2.buyer.outgoing.purchaseRejected;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.PurchaseRejectedMessage;


public class PurchaseRejectedReplyTo extends AbstractDelegate implements PurchaseRejectedReply {
	
	private static final Log log = LogFactory.getLog(PurchaseRejectedReplyTo.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2.buyer";
	}
	
	@Override
	public String getServiceId() {
		return PurchaseRejectedReply.ID;
	}
	
	@SuppressWarnings("unchecked")
	public PurchaseRejectedReply getProxy() throws Exception {
		return getProxy(PurchaseRejectedReply.ID);
	}
	
	@Override
	public void purchaseRejected(PurchaseRejectedMessage purchaseRejectedMessage) {
		try {
			getProxy().purchaseRejected(purchaseRejectedMessage);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
