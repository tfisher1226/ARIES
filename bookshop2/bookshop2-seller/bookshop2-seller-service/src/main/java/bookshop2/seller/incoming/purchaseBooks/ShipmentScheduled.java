package bookshop2.seller.incoming.purchaseBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ShipmentScheduledMessage;


@WebService(name = "shipmentScheduled", targetNamespace = "http://bookshop2/seller")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipmentScheduled {
	
	public String ID = "bookshop2.seller.shipmentScheduled";
	
	public void shipmentScheduled(ShipmentScheduledMessage shipmentScheduledMessage);
	
}
