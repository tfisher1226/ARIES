package bookshop2.seller.incoming.purchaseBooks;

import org.aries.tx.Transactional;

import bookshop2.ShipmentFailedMessage;


public interface ShipmentFailedHandler extends Transactional {
	
	public void shipmentFailed(ShipmentFailedMessage shipmentFailedMessage);
	
}
