package bookshop2.buyer.incoming.orderBooks;

import org.aries.tx.Transactional;

import bookshop2.OrderRejectedMessage;


public interface OrderRejectedHandler extends Transactional {
	
	public void orderRejected(OrderRejectedMessage orderRejectedMessage);
	
}
