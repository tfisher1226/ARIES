package bookshop2.shipper.client.shipBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentRequestMessage;


public class ShipBooksClient extends AbstractDelegate implements ShipBooks {
	
	private static final Log log = LogFactory.getLog(ShipBooksClient.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2.shipper";
	}
	
	@Override
	public String getServiceId() {
		return ShipBooks.ID;
	}
	
	@SuppressWarnings("unchecked")
	public ShipBooks getProxy() throws Exception {
		return getProxy(ShipBooks.ID);
	}
	
	@Override
	public void shipBooks(ShipmentRequestMessage shipmentRequestMessage) {
		try {
			getProxy().shipBooks(shipmentRequestMessage);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
