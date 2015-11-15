package bookshop2.client.paymentService;

import java.util.List;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.Payment;


@SuppressWarnings("serial")
public class PaymentServiceInterceptor extends MessageInterceptor<PaymentService> implements PaymentService {
	
	@Override
	public List<Payment> getAllPaymentRecords() {
		try {
			log.info("#### [admin]: getAllPaymentRecords() sending...");
			Message request = createMessage("getAllPaymentRecords");
			Message response = getProxy().invoke(request);
		List<Payment> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Payment getPaymentRecordById(Long id) {
		try {
			log.info("#### [admin]: getPaymentRecordById() sending...");
			Message request = createMessage("getPaymentRecordById");
			request.addPart("id", id);
			Message response = getProxy().invoke(request);
		Payment result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Payment> getPaymentRecordsByPage(int pageIndex, int pageSize) {
		try {
			log.info("#### [admin]: getPaymentRecordsByPage() sending...");
			Message request = createMessage("getPaymentRecordsByPage");
			request.addPart("pageIndex", pageIndex);
			request.addPart("pageSize", pageSize);
			Message response = getProxy().invoke(request);
		List<Payment> result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addPaymentRecord(Payment payment) {
		try {
			log.info("#### [admin]: addPaymentRecord() sending...");
			Message request = createMessage("addPaymentRecord");
			request.addPart("payment", payment);
			Message response = getProxy().invoke(request);
		Long result = response.getPart("result");
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void savePaymentRecord(Payment payment) {
		try {
			log.info("#### [admin]: savePaymentRecord() sending...");
			Message request = createMessage("savePaymentRecord");
			request.addPart("payment", payment);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllPaymentRecords() {
		try {
			log.info("#### [admin]: removeAllPaymentRecords() sending...");
			Message request = createMessage("removeAllPaymentRecords");
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removePaymentRecord(Payment payment) {
		try {
			log.info("#### [admin]: removePaymentRecord() sending...");
			Message request = createMessage("removePaymentRecord");
			request.addPart("payment", payment);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void importPaymentRecords() {
		try {
			log.info("#### [admin]: importPaymentRecords() sending...");
			Message request = createMessage("importPaymentRecords");
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
