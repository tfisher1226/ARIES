package bookshop2.supplier.incoming.reserveBooks;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import bookshop2.ReservationAbortedException;
import bookshop2.ReservationRequestMessage;


@WebService(name = "reserveBooks", targetNamespace = "http://bookshop2/supplier")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ReserveBooks {
	
	public String ID = "bookshop2.supplier.reserveBooks";
	
	public void reserveBooks(ReservationRequestMessage reservationRequestMessage) throws ReservationAbortedException;
	
}
