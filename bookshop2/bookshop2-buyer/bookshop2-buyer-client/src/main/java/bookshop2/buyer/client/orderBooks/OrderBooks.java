package bookshop2.buyer.client.orderBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.OrderRequestMessage;


@WebService(name = "orderBooks", targetNamespace = "http://bookshop2/buyer")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface OrderBooks {

	public String ID = "bookshop2.buyer.orderBooks";
	
	public void orderBooks(OrderRequestMessage orderRequestMessage);

}
