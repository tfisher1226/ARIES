package bookshop2.buyer.incoming.purchaseBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.PurchaseAcceptedMessage;


@WebService(name = "purchaseAccepted", targetNamespace = "http://bookshop2/buyer")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface PurchaseAccepted {

	public String ID = "bookshop2.buyer.purchaseAccepted";
	
	public void purchaseAccepted(PurchaseAcceptedMessage purchaseAcceptedMessage);

}
