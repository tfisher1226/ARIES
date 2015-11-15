package bookshop2.buyer.outgoing.orderRejected;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "orderRejected", targetNamespace = "http://bookshop2/buyer")
public class OrderRejectedReplyService extends Service {

	private static URL WSDL_LOCATION;

	private OrderRejectedReplyService service;


	public OrderRejectedReplyService() {
		this(WSDL_LOCATION);
	}

	public OrderRejectedReplyService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/buyer", "orderRejectedService"));
	}

	public OrderRejectedReplyService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}


	@WebEndpoint(name = "orderRejected")
	public OrderRejectedReply getPort() {
		return super.getPort(new QName("http://bookshop2/buyer", "orderRejected"), OrderRejectedReply.class);
	}

	@WebEndpoint(name = "orderRejected")
	public OrderRejectedReply getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/buyer", "orderRejected"), OrderRejectedReply.class, features);
	}

}
