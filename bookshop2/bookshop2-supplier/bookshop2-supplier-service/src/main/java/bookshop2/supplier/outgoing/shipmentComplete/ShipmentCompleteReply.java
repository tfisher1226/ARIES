package bookshop2.supplier.outgoing.shipmentComplete;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ShipmentCompleteMessage;


@WebService(name = "shipmentCompleteReply", targetNamespace = "http://bookshop2/supplier")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipmentCompleteReply {
	
	public String ID = "bookshop2.supplier.shipmentCompleteReply";
	
	public void shipmentComplete(ShipmentCompleteMessage shipmentCompleteMessage);
	
}
