package bookshop2.supplier.incoming.shipBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ShipmentRequestMessage;


@WebService(name = "shipBooks", targetNamespace = "http://bookshop2/supplier")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ShipBooks {
	
	public String ID = "bookshop2.supplier.shipBooks";
	
	public void shipBooks(ShipmentRequestMessage shipmentRequestMessage);
	
}
