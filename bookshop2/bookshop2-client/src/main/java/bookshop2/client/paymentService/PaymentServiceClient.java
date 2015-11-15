package bookshop2.client.paymentService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.Payment;


public class PaymentServiceClient extends AbstractDelegate implements PaymentService {
	
	private static final Log log = LogFactory.getLog(PaymentServiceClient.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2";
	}
	
	@Override
	public String getServiceId() {
		return PaymentService.ID;
	}
	
	@SuppressWarnings("unchecked")
	public PaymentService getProxy() throws Exception {
		return getProxy(PaymentService.ID);
	}
	
	@Override
	public List<Payment> getAllPaymentRecords() {
		try {
			List<Payment> result = getProxy().getAllPaymentRecords();
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Payment getPaymentRecordById(Long id) {
		try {
			Payment result = getProxy().getPaymentRecordById(id);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Payment> getPaymentRecordsByPage(int pageIndex, int pageSize) {
		try {
			List<Payment> result = getProxy().getPaymentRecordsByPage(pageIndex, pageSize);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addPaymentRecord(Payment payment) {
		try {
			Long result = getProxy().addPaymentRecord(payment);
			return result;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void savePaymentRecord(Payment payment) {
		try {
			getProxy().savePaymentRecord(payment);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllPaymentRecords() {
		try {
			getProxy().removeAllPaymentRecords();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removePaymentRecord(Payment payment) {
		try {
			getProxy().removePaymentRecord(payment);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void importPaymentRecords() {
		try {
			getProxy().importPaymentRecords();
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
