package bookshop2.client.bookService;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class BookServiceProxyForJAXWS extends JAXWSProxy implements Proxy<BookService> {
	
	private BookServiceService service;
	
	private Object mutex = new Object();
	
	
	public BookServiceProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-service/bookServiceService/bookService?wsdl";
	}
	
	@Override
	public BookService getDelegate() {
		return getPort();
	}
	
	protected BookService getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			BookService port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected BookServiceService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			BookServiceService service = new BookServiceService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
