package bookshop2.supplier.incoming.shipBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ShipmentScheduledMessage;


@WebService(name = "shipmentScheduled", targetNamespace = "http://bookshop2/supplier")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipmentScheduled {
	
	public String ID = "bookshop2.supplier.shipmentScheduled";
	
	public void shipmentScheduled(ShipmentScheduledMessage shipmentScheduledMessage);
	
}
