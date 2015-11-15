package bookshop2.seller.outgoing.orderRejected;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class OrderRejectedReplyProxyForJAXWS extends JAXWSProxy implements Proxy<OrderRejectedReply> {

	private OrderRejectedReplyService service;

	private Object mutex = new Object();

	
	public OrderRejectedReplyProxyForJAXWS(String host, int port) {
		super(host, port);
	}


	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-seller-service/orderRejectedReplyService/orderRejectedReply?wsdl";
	}
	
	@Override
	public OrderRejectedReply getDelegate() {
		return getPort();
	}

	protected OrderRejectedReply getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			OrderRejectedReply port = service.getPort();
			initializePort(port);
			return port;
		}
	}

	protected OrderRejectedReplyService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			OrderRejectedReplyService service = new OrderRejectedReplyService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}

}
