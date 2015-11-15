package bookshop2.buyer.client.purchaseBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.PurchaseRequestMessage;


public class PurchaseBooksClient extends AbstractDelegate implements PurchaseBooks {
	
	private static final Log log = LogFactory.getLog(PurchaseBooksClient.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2.buyer";
	}
	
	@Override
	public String getServiceId() {
		return PurchaseBooks.ID;
	}
	
	@SuppressWarnings("unchecked")
	public PurchaseBooks getProxy() throws Exception {
		return getProxy(PurchaseBooks.ID);
	}
	
	@Override
	public void purchaseBooks(PurchaseRequestMessage purchaseRequestMessage) {
		try {
			getProxy().purchaseBooks(purchaseRequestMessage);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
