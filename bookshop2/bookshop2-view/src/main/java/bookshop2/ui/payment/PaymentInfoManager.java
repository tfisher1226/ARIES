package bookshop2.ui.payment;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import admin.ui.action.ActionListManager;
import admin.ui.action.ActionSelectManager;
import bookshop2.Payment;
import bookshop2.OrderRequestMessage;
import bookshop2.util.PaymentUtil;
import bookshop2.util.Bookshop2Fixture;


@SessionScoped
@Named("paymentInfoManager")
public class PaymentInfoManager extends AbstractRecordManager<Payment> implements Serializable {
	
	private ActionSelectManager actionSelectManager;

	private ActionListManager actionListManager;
	
	@Inject
	@Updated
	private Event<Payment> updatedEvent;
	
	
	public PaymentInfoManager() {
		setInstanceName("payment");
	}


	public Payment getPayment() {
		return getRecord();
	}

	@Override
	public Class<Payment> getRecordClass() {
		return Payment.class;
	}
	
	@Override
	public boolean isEmpty(Payment payment) {
		return getPaymentHelper().isEmpty(payment);
	}
	
	@Override
	public String toString(Payment payment) {
		return getPaymentHelper().toString(payment);
	}
	
	protected PaymentHelper getPaymentHelper() {
		return BeanContext.getFromSession("paymentHelper");
	}
	
	protected void initialize(Payment payment) {
		PaymentUtil.initialize(payment);
		initializeOutjectedState(payment);
		setContext("Payment", payment);
	}
	
	protected void initializeOutjectedState(Payment payment) {
		outject(instanceName, payment);
	}
	
	public void populate() {
		try {
			Payment payment = Bookshop2Fixture.create_Payment();
			initialize(payment);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void activate() {
		initializeContext();
		Payment payment = BeanContext.getFromSession(getInstanceId());
		if (payment == null)
			newPayment();
		else editPayment(payment);
	}
	
	//SEAM @Begin(join = true)
	public void newPayment() {
		try {
			Payment payment = create();
			initialize(payment);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	@Override
	public Payment create() {
		Payment payment = PaymentUtil.create();
		return payment;
	}
	
	@Override
	public Payment clone(Payment payment) {
		payment = PaymentUtil.clone(payment);
		return payment;
	}
	
	//SEAM @Begin(join = true)
	public void editPayment(Payment payment) {
		try {
			//payment = clone(payment);
			initialize(payment);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}

	//SEAM @Observer("org.aries.savePayment")
	public void savePayment() {
		setModule("PaymentDialog");
		Payment payment = getPayment();
		enrichPayment(payment);
		if (validate(payment)) {
			savePayment(payment);
			outject("payment", payment);
			//raiseEvent(actionEvent);
			updatedEvent.fire(payment);
		}
	}

	public void processPayment(Payment payment) {
		Long id = payment.getId();
		if (id != null) {
			savePayment(payment);
		}
	}
	
	public void savePayment(Payment payment) {
		try {
			raiseEvent("org.aries.refreshPaymentList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichPayment(Payment payment) {
		//nothing for now
	}
	
	public boolean validate(Payment payment) {
		Validator validator = getValidator();
		boolean isValid = PaymentUtil.validate(payment);
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}

}
