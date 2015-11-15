package bookshop2.ui.payment;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import bookshop2.Payment;
import bookshop2.util.PaymentUtil;


@SessionScoped
@Named("paymentHelper")
public class PaymentHelper extends AbstractElementHelper<Payment> implements Serializable {

	@Override
	public boolean isEmpty(Payment payment) {
		return PaymentUtil.isEmpty(payment);
	}
	
	@Override
	public String toString(Payment payment) {
		return payment.getTotal() + " " + payment.getCurrency();
	}
	
	@Override
	public String toString(Collection<Payment> paymentList) {
		return PaymentUtil.toString(paymentList);
	}
	
	@Override
	public boolean validate(Payment payment) {
		return PaymentUtil.validate(payment);
	}

	@Override
	public boolean validate(Collection<Payment> paymentList) {
		return PaymentUtil.validate(paymentList);
	}

}
