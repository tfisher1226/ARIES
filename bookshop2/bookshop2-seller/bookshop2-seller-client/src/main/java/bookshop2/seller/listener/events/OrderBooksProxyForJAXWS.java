package bookshop2.seller.listener.events;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class OrderBooksProxyForJAXWS extends JAXWSProxy implements Proxy<OrderBooks> {
	
	private OrderBooksService service;
	
	private Object mutex = new Object();
	
	
	public OrderBooksProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-seller-service/orderBooksService/orderBooks?wsdl";
	}
	
	@Override
	public OrderBooks getDelegate() {
		return getPort();
	}
	
	protected OrderBooks getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			OrderBooks port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected OrderBooksService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			OrderBooksService service = new OrderBooksService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
