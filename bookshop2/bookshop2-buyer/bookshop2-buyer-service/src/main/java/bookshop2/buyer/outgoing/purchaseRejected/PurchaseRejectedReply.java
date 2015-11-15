package bookshop2.buyer.outgoing.purchaseRejected;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.PurchaseRejectedMessage;


@WebService(name = "purchaseRejectedReply", targetNamespace = "http://bookshop2/buyer")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface PurchaseRejectedReply {

	public String ID = "bookshop2.buyer.purchaseRejectedReply";

	public void purchaseRejected(PurchaseRejectedMessage purchaseRejectedMessage);

}
