package bookshop2.client.shipmentService;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class ShipmentServiceProxyForJAXWS extends JAXWSProxy implements Proxy<ShipmentService> {
	
	private ShipmentServiceService service;
	
	private Object mutex = new Object();
	
	
	public ShipmentServiceProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-service/shipmentServiceService/shipmentService?wsdl";
	}
	
	@Override
	public ShipmentService getDelegate() {
		return getPort();
	}
	
	protected ShipmentService getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			ShipmentService port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected ShipmentServiceService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			ShipmentServiceService service = new ShipmentServiceService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
