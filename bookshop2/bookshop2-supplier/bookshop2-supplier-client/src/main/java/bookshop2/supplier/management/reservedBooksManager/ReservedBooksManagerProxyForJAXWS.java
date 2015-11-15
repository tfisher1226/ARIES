package bookshop2.supplier.management.reservedBooksManager;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class ReservedBooksManagerProxyForJAXWS extends JAXWSProxy implements Proxy<ReservedBooksManager> {
	
	private ReservedBooksManagerService service;
	
	private Object mutex = new Object();
	
	
	public ReservedBooksManagerProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-supplier-service/reservedBooksManagerService/reservedBooksManager?wsdl";
	}
	
	@Override
	public ReservedBooksManager getDelegate() {
		return getPort();
	}
	
	protected ReservedBooksManager getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			ReservedBooksManager port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected ReservedBooksManagerService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			ReservedBooksManagerService service = new ReservedBooksManagerService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
