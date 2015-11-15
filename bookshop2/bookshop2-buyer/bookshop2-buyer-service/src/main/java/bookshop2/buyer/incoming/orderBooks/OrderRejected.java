package bookshop2.buyer.incoming.orderBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.OrderRejectedMessage;


@WebService(name = "orderRejected", targetNamespace = "http://bookshop2/buyer")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface OrderRejected {

	public String ID = "bookshop2.buyer.orderRejected";
	
	public void orderRejected(OrderRejectedMessage orderRejectedMessage);

}
