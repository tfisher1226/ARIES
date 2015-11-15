package bookshop2.seller.client.purchaseBooks;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "purchaseBooks", targetNamespace = "http://bookshop2/seller")
public class PurchaseBooksService extends Service {

	private static URL WSDL_LOCATION;

	private PurchaseBooksService service;


	public PurchaseBooksService() {
		this(WSDL_LOCATION);
	}

	public PurchaseBooksService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/seller", "purchaseBooksService"));
	}

	public PurchaseBooksService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}


	@WebEndpoint(name = "purchaseBooks")
	public PurchaseBooks getPort() {
		return super.getPort(new QName("http://bookshop2/seller", "purchaseBooks"), PurchaseBooks.class);
	}

	@WebEndpoint(name = "purchaseBooks")
	public PurchaseBooks getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/seller", "purchaseBooks"), PurchaseBooks.class, features);
	}

}
