package bookshop2.seller.outgoing.orderRejected;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.OrderRejectedMessage;


@WebService(name = "orderRejectedReply", targetNamespace = "http://bookshop2/seller")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface OrderRejectedReply {

	public String ID = "bookshop2.seller.orderRejectedReply";
	
	public void orderRejected(OrderRejectedMessage orderRejectedMessage);

}
