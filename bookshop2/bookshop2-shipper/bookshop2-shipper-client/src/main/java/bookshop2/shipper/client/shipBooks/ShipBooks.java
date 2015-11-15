package bookshop2.shipper.client.shipBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ShipmentRequestMessage;


@WebService(name = "shipBooks", targetNamespace = "http://bookshop2/shipper")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipBooks {
	
	public String ID = "bookshop2.shipper.shipBooks";
	
	public void shipBooks(ShipmentRequestMessage shipmentRequestMessage);
	
}
