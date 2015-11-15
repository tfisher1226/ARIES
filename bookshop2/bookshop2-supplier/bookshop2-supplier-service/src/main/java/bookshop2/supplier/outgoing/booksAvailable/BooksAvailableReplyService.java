package bookshop2.supplier.outgoing.booksAvailable;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "booksAvailable", targetNamespace = "http://bookshop2/supplier")
public class BooksAvailableReplyService extends Service {

	private static URL WSDL_LOCATION;

	private BooksAvailableReplyService service;


	public BooksAvailableReplyService() {
		this(WSDL_LOCATION);
	}

	public BooksAvailableReplyService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/supplier", "booksAvailableService"));
	}

	public BooksAvailableReplyService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}


	@WebEndpoint(name = "booksAvailable")
	public BooksAvailableReply getPort() {
		return super.getPort(new QName("http://bookshop2/supplier", "booksAvailable"), BooksAvailableReply.class);
	}

	@WebEndpoint(name = "booksAvailable")
	public BooksAvailableReply getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/supplier", "booksAvailable"), BooksAvailableReply.class, features);
	}

}
