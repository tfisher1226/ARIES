package bookshop2.client.invoiceService;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "invoiceService", targetNamespace = "http://bookshop2")
public class InvoiceServiceService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private InvoiceServiceService service;
	
	
	public InvoiceServiceService() {
		this(WSDL_LOCATION);
	}
	
	public InvoiceServiceService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2", "invoiceServiceService"));
	}
	
	public InvoiceServiceService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "invoiceService")
	public InvoiceService getPort() {
		return super.getPort(new QName("http://bookshop2", "invoiceService"), InvoiceService.class);
	}
	
	@WebEndpoint(name = "invoiceService")
	public InvoiceService getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2", "invoiceService"), InvoiceService.class, features);
	}
	
}
