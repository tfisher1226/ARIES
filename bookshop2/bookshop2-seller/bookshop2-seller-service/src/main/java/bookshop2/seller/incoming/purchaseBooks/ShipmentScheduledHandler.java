package bookshop2.seller.incoming.purchaseBooks;

import org.aries.tx.Transactional;

import bookshop2.ShipmentScheduledMessage;


public interface ShipmentScheduledHandler extends Transactional {
	
	public void shipmentScheduled(ShipmentScheduledMessage shipmentScheduledMessage);
	
}
