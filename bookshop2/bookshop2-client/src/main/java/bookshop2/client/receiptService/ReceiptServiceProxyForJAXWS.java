package bookshop2.client.receiptService;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class ReceiptServiceProxyForJAXWS extends JAXWSProxy implements Proxy<ReceiptService> {
	
	private ReceiptServiceService service;
	
	private Object mutex = new Object();
	
	
	public ReceiptServiceProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-service/receiptServiceService/receiptService?wsdl";
	}
	
	@Override
	public ReceiptService getDelegate() {
		return getPort();
	}
	
	protected ReceiptService getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			ReceiptService port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected ReceiptServiceService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			ReceiptServiceService service = new ReceiptServiceService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
