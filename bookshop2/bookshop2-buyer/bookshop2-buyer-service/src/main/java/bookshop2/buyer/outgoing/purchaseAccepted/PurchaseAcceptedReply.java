package bookshop2.buyer.outgoing.purchaseAccepted;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.PurchaseAcceptedMessage;


@WebService(name = "purchaseAcceptedReply", targetNamespace = "http://bookshop2/buyer")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface PurchaseAcceptedReply {

	public String ID = "bookshop2.buyer.purchaseAcceptedReply";
	
	public void purchaseAccepted(PurchaseAcceptedMessage purchaseAcceptedMessage);

}
