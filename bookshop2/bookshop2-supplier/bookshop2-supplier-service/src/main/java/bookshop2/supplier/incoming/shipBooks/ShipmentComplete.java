package bookshop2.supplier.incoming.shipBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ShipmentCompleteMessage;


@WebService(name = "shipmentComplete", targetNamespace = "http://bookshop2/supplier")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipmentComplete {
	
	public String ID = "bookshop2.supplier.shipmentComplete";
	
	public void shipmentComplete(ShipmentCompleteMessage shipmentCompleteMessage);
	
}
