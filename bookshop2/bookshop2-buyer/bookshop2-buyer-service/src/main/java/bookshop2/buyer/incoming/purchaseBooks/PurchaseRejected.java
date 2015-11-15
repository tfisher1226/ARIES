package bookshop2.buyer.incoming.purchaseBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.PurchaseRejectedMessage;


@WebService(name = "purchaseRejected", targetNamespace = "http://bookshop2/buyer")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface PurchaseRejected {

	public String ID = "bookshop2.buyer.purchaseRejected";
	
	public void purchaseRejected(PurchaseRejectedMessage purchaseRejectedMessage);

}
