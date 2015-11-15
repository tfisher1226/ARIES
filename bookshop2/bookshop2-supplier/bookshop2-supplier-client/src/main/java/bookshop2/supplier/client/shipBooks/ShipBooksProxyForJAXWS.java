package bookshop2.supplier.client.shipBooks;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class ShipBooksProxyForJAXWS extends JAXWSProxy implements Proxy<ShipBooks> {
	
	private ShipBooksService service;
	
	private Object mutex = new Object();
	
	
	public ShipBooksProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-supplier-service/shipBooksService/shipBooks?wsdl";
	}
	
	@Override
	public ShipBooks getDelegate() {
		return getPort();
	}
	
	protected ShipBooks getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			ShipBooks port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected ShipBooksService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			ShipBooksService service = new ShipBooksService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
