package bookshop2.client.invoiceService;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class InvoiceServiceProxyForJAXWS extends JAXWSProxy implements Proxy<InvoiceService> {
	
	private InvoiceServiceService service;
	
	private Object mutex = new Object();
	
	
	public InvoiceServiceProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-service/invoiceServiceService/invoiceService?wsdl";
	}
	
	@Override
	public InvoiceService getDelegate() {
		return getPort();
	}
	
	protected InvoiceService getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			InvoiceService port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected InvoiceServiceService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			InvoiceServiceService service = new InvoiceServiceService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
