package bookshop2.supplier.client.reserveBooks;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class ReserveBooksProxyForJAXWS extends JAXWSProxy implements Proxy<ReserveBooks> {
	
	private ReserveBooksService service;
	
	private Object mutex = new Object();
	
	
	public ReserveBooksProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-supplier-service/reserveBooksService/reserveBooks?wsdl";
	}
	
	@Override
	public ReserveBooks getDelegate() {
		return getPort();
	}
	
	protected ReserveBooks getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			ReserveBooks port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected ReserveBooksService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			ReserveBooksService service = new ReserveBooksService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
