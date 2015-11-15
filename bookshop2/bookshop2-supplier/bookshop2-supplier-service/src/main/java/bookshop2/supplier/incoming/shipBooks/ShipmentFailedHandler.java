package bookshop2.supplier.incoming.shipBooks;

import org.aries.tx.Transactional;

import bookshop2.ShipmentFailedMessage;


public interface ShipmentFailedHandler extends Transactional {
	
	public void shipmentFailed(ShipmentFailedMessage shipmentFailedMessage);
	
}
