package bookshop2.buyer.outgoing.purchaseAccepted;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "purchaseAccepted", targetNamespace = "http://bookshop2/buyer")
public class PurchaseAcceptedReplyService extends Service {

	private static URL WSDL_LOCATION;

	private PurchaseAcceptedReplyService service;


	public PurchaseAcceptedReplyService() {
		this(WSDL_LOCATION);
	}

	public PurchaseAcceptedReplyService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/buyer", "purchaseAcceptedService"));
	}

	public PurchaseAcceptedReplyService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}


	@WebEndpoint(name = "purchaseAccepted")
	public PurchaseAcceptedReply getPort() {
		return super.getPort(new QName("http://bookshop2/buyer", "purchaseAccepted"), PurchaseAcceptedReply.class);
	}

	@WebEndpoint(name = "purchaseAccepted")
	public PurchaseAcceptedReply getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/buyer", "purchaseAccepted"), PurchaseAcceptedReply.class, features);
	}

}
