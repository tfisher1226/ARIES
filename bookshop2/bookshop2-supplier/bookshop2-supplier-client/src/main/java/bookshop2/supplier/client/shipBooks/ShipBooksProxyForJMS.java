package bookshop2.supplier.client.shipBooks;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentRequestMessage;


public class ShipBooksProxyForJMS extends JMSProxy implements Proxy<ShipBooks> {

	private static final String DESTINATION = "/queue/inventory_bookshop2_supplier_ship_books_queue";
	
	private ShipBooksInterceptor shipBooksInterceptor;
	
	
	public ShipBooksProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		shipBooksInterceptor = new ShipBooksInterceptor();
		shipBooksInterceptor.setProxy(this);
	}
	
	@Override
	public ShipBooks getDelegate() {
		return shipBooksInterceptor;
	}
	
	public void shipBooks(ShipmentRequestMessage shipmentRequestMessage) {
		//Check.isValid("shipmentRequestMessage", shipmentRequestMessage);
		try {
			send(shipmentRequestMessage);
			log.info("#### [supplier-client]: ShipBooks request sent");
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
