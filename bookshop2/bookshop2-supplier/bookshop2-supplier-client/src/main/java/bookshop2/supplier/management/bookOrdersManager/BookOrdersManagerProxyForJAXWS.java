package bookshop2.supplier.management.bookOrdersManager;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class BookOrdersManagerProxyForJAXWS extends JAXWSProxy implements Proxy<BookOrdersManager> {
	
	private BookOrdersManagerService service;
	
	private Object mutex = new Object();
	
	
	public BookOrdersManagerProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-supplier-service/bookOrdersManagerService/bookOrdersManager?wsdl";
	}
	
	@Override
	public BookOrdersManager getDelegate() {
		return getPort();
	}
	
	protected BookOrdersManager getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			BookOrdersManager port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected BookOrdersManagerService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			BookOrdersManagerService service = new BookOrdersManagerService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
