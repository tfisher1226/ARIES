package bookshop2.seller.outgoing.orderAccepted;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "orderAcceptedReply", targetNamespace = "http://bookshop2/seller")
public class OrderAcceptedReplyService extends Service {

	private static URL WSDL_LOCATION;

	private OrderAcceptedReplyService service;


	public OrderAcceptedReplyService() {
		this(WSDL_LOCATION);
	}

	public OrderAcceptedReplyService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/seller", "orderAcceptedReplyService"));
	}

	public OrderAcceptedReplyService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}


	@WebEndpoint(name = "orderAcceptedReply")
	public OrderAcceptedReply getPort() {
		return super.getPort(new QName("http://bookshop2/seller", "orderAcceptedReply"), OrderAcceptedReply.class);
	}

	@WebEndpoint(name = "orderAcceptedReply")
	public OrderAcceptedReply getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/seller", "orderAcceptedReply"), OrderAcceptedReply.class, features);
	}

}
