package bookshop2.supplier.client.reserveBooks;

import org.aries.bean.Proxy;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.ReservationAbortedException;
import bookshop2.ReservationRequestMessage;


public class ReserveBooksProxyForJMS extends JMSProxy implements Proxy<ReserveBooks> {
	
	private static final String DESTINATION = "/queue/inventory_bookshop2_supplier_reserve_books_queue";

	private ReserveBooksInterceptor reserveBooksInterceptor;


	public ReserveBooksProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		reserveBooksInterceptor = new ReserveBooksInterceptor();
		reserveBooksInterceptor.setProxy(this);
	}
	
	@Override
	public ReserveBooks getDelegate() {
		return reserveBooksInterceptor;
	}

	//@Override
	public void reserveBooks(ReservationRequestMessage reservationRequestMessage) throws ReservationAbortedException {
		//Check.isValid("reservationRequestMessage", reservationRequestMessage);
		try {
			if (getDestinationName() == null)
				send(DESTINATION, reservationRequestMessage);
			else send(reservationRequestMessage);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
