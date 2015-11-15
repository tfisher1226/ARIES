package bookshop2.buyer.outgoing.orderAccepted;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.OrderAcceptedMessage;


@WebService(name = "orderAcceptedReply",	targetNamespace = "http://bookshop2/buyer")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface OrderAcceptedReply {

	public String ID = "bookshop2.buyer.orderAcceptedReply";

	public void orderAccepted(OrderAcceptedMessage orderAcceptedMessage);

}
