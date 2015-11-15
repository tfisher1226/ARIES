package bookshop2.buyer.incoming.purchaseBooks;

import org.aries.tx.Transactional;

import bookshop2.PurchaseRejectedMessage;


public interface PurchaseRejectedHandler extends Transactional {
	
	public void purchaseRejected(PurchaseRejectedMessage purchaseRejectedMessage);
	
}
