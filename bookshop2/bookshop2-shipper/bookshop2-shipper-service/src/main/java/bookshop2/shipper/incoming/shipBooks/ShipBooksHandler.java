package bookshop2.shipper.incoming.shipBooks;

import org.aries.tx.Transactional;

import bookshop2.ShipmentRequestMessage;


public interface ShipBooksHandler extends Transactional {
	
	public void shipBooks(ShipmentRequestMessage shipmentRequestMessage);
	
}