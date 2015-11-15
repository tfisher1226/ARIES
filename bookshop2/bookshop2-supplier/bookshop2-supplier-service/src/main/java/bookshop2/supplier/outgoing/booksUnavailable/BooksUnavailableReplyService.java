package bookshop2.supplier.outgoing.booksUnavailable;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "booksUnavailable", targetNamespace = "http://bookshop2/supplier")
public class BooksUnavailableReplyService extends Service {

	private static URL WSDL_LOCATION;

	private BooksUnavailableReplyService service;


	public BooksUnavailableReplyService() {
		this(WSDL_LOCATION);
	}

	public BooksUnavailableReplyService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/supplier", "booksUnavailableService"));
	}

	public BooksUnavailableReplyService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}


	@WebEndpoint(name = "booksUnavailable")
	public BooksUnavailableReply getPort() {
		return super.getPort(new QName("http://bookshop2/supplier", "booksUnavailable"), BooksUnavailableReply.class);
	}

	@WebEndpoint(name = "booksUnavailable")
	public BooksUnavailableReply getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/supplier", "booksUnavailable"), BooksUnavailableReply.class, features);
	}

}
