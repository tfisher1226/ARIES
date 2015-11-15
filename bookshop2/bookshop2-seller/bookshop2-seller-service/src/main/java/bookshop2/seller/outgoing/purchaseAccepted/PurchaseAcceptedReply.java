package bookshop2.seller.outgoing.purchaseAccepted;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.PurchaseAcceptedMessage;


@WebService(name = "purchaseAcceptedReply", targetNamespace = "http://bookshop2/seller")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface PurchaseAcceptedReply {

	public String ID = "bookshop2.seller.purchaseAcceptedReply";
	
	public void purchaseAccepted(PurchaseAcceptedMessage purchaseAcceptedMessage);

}
