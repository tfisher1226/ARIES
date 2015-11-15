package bookshop2.client.receiptService;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "receiptService", targetNamespace = "http://bookshop2")
public class ReceiptServiceService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private ReceiptServiceService service;
	
	
	public ReceiptServiceService() {
		this(WSDL_LOCATION);
	}
	
	public ReceiptServiceService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2", "receiptServiceService"));
	}
	
	public ReceiptServiceService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "receiptService")
	public ReceiptService getPort() {
		return super.getPort(new QName("http://bookshop2", "receiptService"), ReceiptService.class);
	}
	
	@WebEndpoint(name = "receiptService")
	public ReceiptService getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2", "receiptService"), ReceiptService.class, features);
	}
	
}
