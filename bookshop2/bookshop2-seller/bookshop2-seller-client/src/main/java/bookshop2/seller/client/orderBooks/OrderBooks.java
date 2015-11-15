package bookshop2.seller.client.orderBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.OrderRequestMessage;


@WebService(name = "orderBooks", targetNamespace = "http://bookshop2/seller")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface OrderBooks {
	
	public String ID = "bookshop2.seller.orderBooks";

	public void orderBooks(OrderRequestMessage orderRequestMessage);

}
