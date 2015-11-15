package bookshop2.buyer.incoming.purchaseBooks;

import org.aries.tx.Transactional;

import bookshop2.PurchaseAcceptedMessage;


public interface PurchaseAcceptedHandler extends Transactional {
	
	public void purchaseAccepted(PurchaseAcceptedMessage purchaseAcceptedMessage);
	
}
