package bookshop2.ui.payment;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;

import bookshop2.Payment;
import bookshop2.util.PaymentUtil;


@SessionScoped
@Named("paymentListManager")
public class PaymentListManager extends AbstractTabListManager<Payment, PaymentListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "PaymentList";
	}

	@Override
	public String getTitle() {
		return "Payment List";
	}

	@Override
	public Object getRecordId(Payment payment) {
		return payment.getId();
	}

	@Override
	public String getRecordName(Payment payment) {
		return PaymentUtil.toString(payment);
	}

	@Override
	protected Class<Payment> getRecordClass() {
		return Payment.class;
	}

	@Override
	protected Payment getRecord(PaymentListObject rowObject) {
		return rowObject.getPayment();
	}
	
	@Override
	protected PaymentListObject createRowObject(Payment payment) {
		return new PaymentListObject(payment);
	}

	protected PaymentHelper getPaymentHelper() {
		return BeanContext.getFromSession("paymentHelper");
	}
	
	protected PaymentInfoManager getPaymentInfoManager() {
		return BeanContext.getFromSession("paymentInfoManager");
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	public void refreshModel() {
		refreshModel(recordList);
	}
	
	public void editPayment() {
		editPayment(selectedRecordId);
	}
	
	public void editPayment(String recordId) {
		editPayment(Long.parseLong(recordId));
	}
	
	public void editPayment(Long recordId) {
		Payment payment = recordByIdMap.get(recordId);
		editPayment(payment);
	}
	
	public void editPayment(Payment payment) {
		PaymentInfoManager paymentInfoManager = BeanContext.getFromSession("paymentInfoManager");
		paymentInfoManager.editPayment(payment);
	}
	
	//SEAM @Observer("org.aries.removePayment")
	public void removePayment() {
		removePayment(selectedRecordId);
	}
	
	public void removePayment(String recordId) {
		removePayment(Long.parseLong(recordId));
	}
	
	public void removePayment(Long recordId) {
		Payment payment = recordByIdMap.get(recordId);
		removePayment(payment);
	}
	
	public void removePayment(Payment payment) {
			try {
				clearSelection();
				refresh();
			
			} catch (Exception e) {
			handleException(e);
		}
	}
	
	//SEAM @Observer("org.aries.cancelPayment")
	public void cancelPayment() {
		if (selectedRecord == null)
			return;
		try {
			BeanContext.removeFromSession("payment");
			Long id = selectedRecord.getId();
			initialize(recordByIdMap.values());
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public boolean validatePayment(Collection<Payment> paymentList) {
		return PaymentUtil.validate(paymentList);
	}
	
}
