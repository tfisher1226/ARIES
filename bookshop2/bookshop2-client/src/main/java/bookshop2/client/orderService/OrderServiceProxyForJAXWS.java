package bookshop2.client.orderService;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class OrderServiceProxyForJAXWS extends JAXWSProxy implements Proxy<OrderService> {
	
	private OrderServiceService service;
	
	private Object mutex = new Object();
	
	
	public OrderServiceProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-service/orderServiceService/orderService?wsdl";
	}
	
	@Override
	public OrderService getDelegate() {
		return getPort();
	}
	
	protected OrderService getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			OrderService port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected OrderServiceService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			OrderServiceService service = new OrderServiceService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
