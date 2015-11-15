package bookshop2.shipper.outgoing.shipmentFailed;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ShipmentFailedMessage;


@WebService(name = "shipmentFailedReply", targetNamespace = "http://bookshop2/shipper")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipmentFailedReply {
	
	public String ID = "bookshop2.shipper.shipmentFailedReply";
	
	public void shipmentFailed(ShipmentFailedMessage shipmentFailedMessage);
	
}
