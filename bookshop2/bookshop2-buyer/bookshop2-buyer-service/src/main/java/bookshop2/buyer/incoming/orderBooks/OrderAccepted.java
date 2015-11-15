package bookshop2.buyer.incoming.orderBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.OrderAcceptedMessage;


@WebService(name = "orderAccepted", targetNamespace = "http://bookshop2/buyer")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface OrderAccepted {

	public String ID = "bookshop2.buyer.orderAccepted";
	
	public void orderAccepted(OrderAcceptedMessage orderAcceptedMessage);

}
