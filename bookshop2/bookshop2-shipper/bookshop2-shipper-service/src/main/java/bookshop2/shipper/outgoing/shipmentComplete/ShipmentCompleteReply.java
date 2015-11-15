package bookshop2.shipper.outgoing.shipmentComplete;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ShipmentCompleteMessage;


@WebService(name = "shipmentCompleteReply", targetNamespace = "http://bookshop2/shipper")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipmentCompleteReply {
	
	public String ID = "bookshop2.shipper.shipmentCompleteReply";
	
	public void shipmentComplete(ShipmentCompleteMessage shipmentCompleteMessage);
	
}
