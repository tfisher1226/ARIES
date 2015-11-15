package bookshop2.buyer.outgoing.purchaseAccepted;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.PurchaseAcceptedMessage;


public class PurchaseAcceptedReplyTo extends AbstractDelegate implements PurchaseAcceptedReply {
	
	private static final Log log = LogFactory.getLog(PurchaseAcceptedReplyTo.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2.buyer";
	}
	
	@Override
	public String getServiceId() {
		return PurchaseAcceptedReply.ID;
	}
	
	@SuppressWarnings("unchecked")
	public PurchaseAcceptedReply getProxy() throws Exception {
		return getProxy(PurchaseAcceptedReply.ID);
	}
	
	@Override
	public void purchaseAccepted(PurchaseAcceptedMessage purchaseAcceptedMessage) {
		try {
			getProxy().purchaseAccepted(purchaseAcceptedMessage);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
