package bookshop2.supplier.incoming.shipBooks;

import org.aries.tx.Transactional;

import bookshop2.ShipmentCompleteMessage;


public interface ShipmentCompleteHandler extends Transactional {
	
	public void shipmentComplete(ShipmentCompleteMessage shipmentCompleteMessage);
	
}
