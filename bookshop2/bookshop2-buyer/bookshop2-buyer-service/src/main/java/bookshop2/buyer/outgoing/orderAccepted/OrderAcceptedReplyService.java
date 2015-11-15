package bookshop2.buyer.outgoing.orderAccepted;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "orderAccepted", targetNamespace = "http://bookshop2/buyer")
public class OrderAcceptedReplyService extends Service {

	private static URL WSDL_LOCATION;

	private OrderAcceptedReplyService service;


	public OrderAcceptedReplyService() {
		this(WSDL_LOCATION);
	}

	public OrderAcceptedReplyService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/buyer", "orderAcceptedService"));
	}

	public OrderAcceptedReplyService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}


	@WebEndpoint(name = "orderAccepted")
	public OrderAcceptedReply getPort() {
		return super.getPort(new QName("http://bookshop2/buyer", "orderAccepted"), OrderAcceptedReply.class);
	}

	@WebEndpoint(name = "orderAccepted")
	public OrderAcceptedReply getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/buyer", "orderAccepted"), OrderAcceptedReply.class, features);
	}

}
