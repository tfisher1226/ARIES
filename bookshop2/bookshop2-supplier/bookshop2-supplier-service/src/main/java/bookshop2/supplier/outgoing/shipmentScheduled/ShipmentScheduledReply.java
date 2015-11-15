package bookshop2.supplier.outgoing.shipmentScheduled;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ShipmentScheduledMessage;


@WebService(name = "shipmentScheduledReply", targetNamespace = "http://bookshop2/supplier")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipmentScheduledReply {
	
	public String ID = "bookshop2.supplier.shipmentScheduledReply";
	
	public void shipmentScheduled(ShipmentScheduledMessage shipmentScheduledMessage);
	
}
