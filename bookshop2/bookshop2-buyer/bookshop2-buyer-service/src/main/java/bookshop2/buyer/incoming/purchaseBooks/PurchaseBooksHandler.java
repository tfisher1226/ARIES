package bookshop2.buyer.incoming.purchaseBooks;

import org.aries.tx.Transactional;

import bookshop2.PurchaseRequestMessage;


public interface PurchaseBooksHandler extends Transactional {
	
	public void purchaseBooks(PurchaseRequestMessage purchaseRequestMessage);
	
}