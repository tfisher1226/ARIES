package bookshop2.supplier.client.queryBooks;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class QueryBooksProxyForJAXWS extends JAXWSProxy implements Proxy<QueryBooks> {
	
	private QueryBooksService service;
	
	private Object mutex = new Object();
	
	
	public QueryBooksProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-supplier-service/queryBooksService/queryBooks?wsdl";
	}
	
	@Override
	public QueryBooks getDelegate() {
		return getPort();
	}
	
	protected QueryBooks getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			QueryBooks port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected QueryBooksService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			QueryBooksService service = new QueryBooksService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
