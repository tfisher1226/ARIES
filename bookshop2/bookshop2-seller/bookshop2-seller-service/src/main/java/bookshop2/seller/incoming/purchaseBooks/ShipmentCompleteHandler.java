package bookshop2.seller.incoming.purchaseBooks;

import org.aries.tx.Transactional;

import bookshop2.ShipmentCompleteMessage;


public interface ShipmentCompleteHandler extends Transactional {
	
	public void shipmentComplete(ShipmentCompleteMessage shipmentCompleteMessage);
	
}
