package bookshop2.buyer.client.purchaseBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.PurchaseRequestMessage;


@WebService(name = "purchaseBooks", targetNamespace = "http://bookshop2/buyer")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface PurchaseBooks {
	
	public String ID = "bookshop2.buyer.purchaseBooks";
	
	public void purchaseBooks(PurchaseRequestMessage purchaseRequestMessage);
	
}
