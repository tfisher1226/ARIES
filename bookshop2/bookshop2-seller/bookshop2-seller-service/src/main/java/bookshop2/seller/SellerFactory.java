package bookshop2.seller;

import javax.ejb.AccessTimeout;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import bookshop2.Invoice;
import bookshop2.Shipment;


@Startup
@Singleton
@LocalBean
@AccessTimeout(value = 60000)
public class SellerFactory {
	
	private bookshop2.ObjectFactory bookshop2ObjectFactory = new bookshop2.ObjectFactory();
	
	
	public Shipment createShipment() {
		return bookshop2ObjectFactory.createShipment();
	}

	public Invoice createInvoice() {
		return bookshop2ObjectFactory.createInvoice();
	}

}
