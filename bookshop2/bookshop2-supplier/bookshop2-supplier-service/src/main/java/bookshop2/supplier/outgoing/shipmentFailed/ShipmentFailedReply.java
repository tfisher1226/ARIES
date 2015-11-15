package bookshop2.supplier.outgoing.shipmentFailed;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ShipmentFailedMessage;


@WebService(name = "shipmentFailedReply", targetNamespace = "http://bookshop2/supplier")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipmentFailedReply {
	
	public String ID = "bookshop2.supplier.shipmentFailedReply";
	
	public void shipmentFailed(ShipmentFailedMessage shipmentFailedMessage);
	
}
