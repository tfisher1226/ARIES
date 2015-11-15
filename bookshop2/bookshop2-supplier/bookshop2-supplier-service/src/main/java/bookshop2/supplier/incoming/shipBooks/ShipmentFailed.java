package bookshop2.supplier.incoming.shipBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ShipmentFailedMessage;


@WebService(name = "shipmentFailed", targetNamespace = "http://bookshop2/supplier")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipmentFailed {
	
	public String ID = "bookshop2.supplier.shipmentFailed";
	
	public void shipmentFailed(ShipmentFailedMessage shipmentFailedMessage);
	
}
