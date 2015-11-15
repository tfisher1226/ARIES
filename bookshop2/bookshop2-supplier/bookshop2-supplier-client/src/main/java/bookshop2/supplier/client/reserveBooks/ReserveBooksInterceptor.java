package bookshop2.supplier.client.reserveBooks;

import org.aries.bean.Proxy;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.ReservationAbortedException;
import bookshop2.ReservationRequestMessage;


public class ReserveBooksInterceptor extends MessageInterceptor<ReserveBooks> implements ReserveBooks {
	
	@Override
	public void reserveBooks(ReservationRequestMessage reservationRequestMessage) throws ReservationAbortedException {
		try {
			log.info("#### [supplier-client]: reserveBooks() sending");
			Message message = createMessage("reserveBooks");
			message.addPart("reservationRequestMessage", reservationRequestMessage);
			message.addPart("correlationId", reservationRequestMessage.getCorrelationId());
			message.addPart("transactionId", reservationRequestMessage.getTransactionId());
			Proxy<ReserveBooks> proxy = getProxy();
			proxy.send(message);

		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
