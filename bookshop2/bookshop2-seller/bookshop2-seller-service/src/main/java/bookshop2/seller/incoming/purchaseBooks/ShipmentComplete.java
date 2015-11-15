package bookshop2.seller.incoming.purchaseBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ShipmentCompleteMessage;


@WebService(name = "shipmentComplete", targetNamespace = "http://bookshop2/seller")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipmentComplete {
	
	public String ID = "bookshop2.seller.shipmentComplete";
	
	public void shipmentComplete(ShipmentCompleteMessage shipmentCompleteMessage);
	
}
