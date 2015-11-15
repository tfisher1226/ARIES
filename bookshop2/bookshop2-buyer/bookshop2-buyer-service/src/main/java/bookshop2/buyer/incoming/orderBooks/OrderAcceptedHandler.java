package bookshop2.buyer.incoming.orderBooks;

import org.aries.tx.Transactional;

import bookshop2.OrderAcceptedMessage;


public interface OrderAcceptedHandler extends Transactional {
	
	public void orderAccepted(OrderAcceptedMessage orderAcceptedMessage);
	
}
