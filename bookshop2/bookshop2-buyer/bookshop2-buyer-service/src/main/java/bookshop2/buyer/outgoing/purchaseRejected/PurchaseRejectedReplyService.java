package bookshop2.buyer.outgoing.purchaseRejected;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "purchaseRejected", targetNamespace = "http://bookshop2/buyer")
public class PurchaseRejectedReplyService extends Service {

	private static URL WSDL_LOCATION;

	private PurchaseRejectedReplyService service;


	public PurchaseRejectedReplyService() {
		this(WSDL_LOCATION);
	}

	public PurchaseRejectedReplyService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/buyer", "purchaseRejectedService"));
	}

	public PurchaseRejectedReplyService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}


	@WebEndpoint(name = "purchaseRejected")
	public PurchaseRejectedReply getPort() {
		return super.getPort(new QName("http://bookshop2/buyer", "purchaseRejected"), PurchaseRejectedReply.class);
	}

	@WebEndpoint(name = "purchaseRejected")
	public PurchaseRejectedReply getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/buyer", "purchaseRejected"), PurchaseRejectedReply.class, features);
	}

}
