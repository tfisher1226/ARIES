package bookshop2.client.paymentService;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class PaymentServiceProxyForJAXWS extends JAXWSProxy implements Proxy<PaymentService> {
	
	private PaymentServiceService service;
	
	private Object mutex = new Object();
	
	
	public PaymentServiceProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-service/paymentServiceService/paymentService?wsdl";
	}
	
	@Override
	public PaymentService getDelegate() {
		return getPort();
	}
	
	protected PaymentService getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			PaymentService port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected PaymentServiceService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			PaymentServiceService service = new PaymentServiceService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
