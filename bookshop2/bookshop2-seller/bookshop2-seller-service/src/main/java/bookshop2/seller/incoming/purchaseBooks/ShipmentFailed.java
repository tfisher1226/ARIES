package bookshop2.seller.incoming.purchaseBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ShipmentFailedMessage;


@WebService(name = "shipmentFailed", targetNamespace = "http://bookshop2/seller")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipmentFailed {
	
	public String ID = "bookshop2.seller.shipmentFailed";
	
	public void shipmentFailed(ShipmentFailedMessage shipmentFailedMessage);
	
}
