package bookshop2.seller.client.purchaseBooks;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class PurchaseBooksProxyForJAXWS extends JAXWSProxy implements Proxy<PurchaseBooks> {
	
	private PurchaseBooksService service;
	
	private Object mutex = new Object();
	
	
	public PurchaseBooksProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-seller-service/purchaseBooksService/purchaseBooks?wsdl";
	}
	
	@Override
	public PurchaseBooks getDelegate() {
		return getPort();
	}
	
	protected PurchaseBooks getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			PurchaseBooks port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected PurchaseBooksService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			PurchaseBooksService service = new PurchaseBooksService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
