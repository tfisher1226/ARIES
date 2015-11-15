package bookshop2.supplier.incoming.reserveBooks;

import org.aries.tx.Transactional;

import bookshop2.ReservationAbortedException;
import bookshop2.ReservationRequestMessage;


public interface ReserveBooksHandler extends Transactional {
	
	public void reserveBooks(ReservationRequestMessage reservationRequestMessage) throws ReservationAbortedException;
	
}
