package bookshop2.buyer.outgoing.orderRejected;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.OrderRejectedMessage;


@WebService(name = "orderRejectedReply", targetNamespace = "http://bookshop2/buyer")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface OrderRejectedReply {

	public String ID = "bookshop2.buyer.orderRejectedReply";
	
	public void orderRejected(OrderRejectedMessage orderRejectedMessage);

}
