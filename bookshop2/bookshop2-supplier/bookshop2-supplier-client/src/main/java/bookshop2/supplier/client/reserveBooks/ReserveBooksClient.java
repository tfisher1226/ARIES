package bookshop2.supplier.client.reserveBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.ReservationAbortedException;
import bookshop2.ReservationRequestMessage;


public class ReserveBooksClient extends AbstractDelegate implements ReserveBooks {
	
	private static final Log log = LogFactory.getLog(ReserveBooksClient.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	@Override
	public String getServiceId() {
		return ReserveBooks.ID;
	}
	
	@SuppressWarnings("unchecked")
	public ReserveBooks getProxy() throws Exception {
		return getProxy(ReserveBooks.ID);
	}
	
	@Override
	public void reserveBooks(ReservationRequestMessage reservationRequestMessage) throws ReservationAbortedException {
		try {
			getProxy().reserveBooks(reservationRequestMessage);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
