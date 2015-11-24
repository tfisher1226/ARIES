package bookshop2.seller.incoming.orderBooks;

import org.aries.tx.Transactional;

import bookshop2.OrderRequestMessage;


public interface OrderBooksHandler extends Transactional {
	
	public void orderBooks(OrderRequestMessage orderRequestMessage);
	
}